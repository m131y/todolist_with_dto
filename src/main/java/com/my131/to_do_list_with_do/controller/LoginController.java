package com.my131.to_do_list_with_do.controller;

import com.my131.to_do_list_with_do.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private  final UserRepository userRepository;

    @GetMapping({"/", "/login"})
    public String showLogin() {
        return "login";
    }
}