package com.my131.to_do_list_with_do.controller;

import com.my131.to_do_list_with_do.dto.LoginDto;
import com.my131.to_do_list_with_do.dto.SignupDto;
import com.my131.to_do_list_with_do.model.User;
import com.my131.to_do_list_with_do.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private  final UserRepository userRepository;

    @GetMapping({"/", "/login"})
    public String showLogin(Model model) {
        model.addAttribute("loginDto", new LoginDto());

        return "login";
    }

    @PostMapping("/login")
    public String doSignup(
            @Valid @ModelAttribute("loginDto") LoginDto loginDto,   //@valid = 검증
            BindingResult bindingResult,    //검증결과 등이 모인다?
            HttpSession httpSession,    //login에는 session필요
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        try {
            User user = userRepository.findByUsername(loginDto.getUsername());
            if(!user.getPassword().equals(loginDto.getPassword())) {
                model.addAttribute("error", "비밀번호가 올바르지 않습니다.");

                return "login";
            }

            httpSession.setAttribute("user", user);
            System.out.println("로그인성공");
            return "redirect:/todos";

        } catch (Exception e) {
            model.addAttribute("error", "존재하지 않는 사용자입니다.");
        }

        User user = User.builder()
                .username(loginDto.getUsername())
                .password(loginDto.getPassword())
                .build();
        userRepository.save(user);

        return "redirect:/login?registered";
    }
}