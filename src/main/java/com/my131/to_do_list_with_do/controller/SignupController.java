package com.my131.to_do_list_with_do.controller;

import com.my131.to_do_list_with_do.dto.SignupDto;
import com.my131.to_do_list_with_do.model.User;
import com.my131.to_do_list_with_do.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignupController {
    private  final UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("signupDto", new SignupDto());

        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(
            @Valid @ModelAttribute() SignupDto signupDto,   //@valid = 검증
            BindingResult bindingResult,    //검증결과 등이 모인다?
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (userRepository.findByUsername(signupDto.getUsername()) != null) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");

            return "signup";
        }


        User user = User.builder()
                .username(signupDto.getUsername())
                .password(signupDto.getPassword())
                .build();
        userRepository.save(user);

        return "redirect:/login?registered";
    }
}