package com.my131.to_do_list_with_do.controller;

import com.my131.to_do_list_with_do.dto.SignupDTO;
import com.my131.to_do_list_with_do.model.User;
import com.my131.to_do_list_with_do.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
        model.addAttribute("signupDto", new SignupDTO());

        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(
            @Valid @ModelAttribute("signupDto") SignupDTO signupDTO,   //@valid = 검증
            BindingResult bindingResult,    //검증결과 등이 모인다?
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        //중복 가입 여부 체크
        User user = User.builder()
                .username(signupDTO.getUsername())
                .password(signupDTO.getPassword())
                .build();
        userRepository.save(user);

        return "redirect:/login?registered";
    }
}