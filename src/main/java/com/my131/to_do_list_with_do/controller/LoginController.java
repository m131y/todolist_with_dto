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

    @GetMapping({"/", "/login"})							//localhost:8080/ 이거나 localhost:8080/login 경로의 GET요청일때 처리
    public String showLogin(Model model) {					//model은 뷰(View)로 데이터를 넘길 때 사용하는 객체
        model.addAttribute("loginDto", new LoginDto());		//로그인 폼에서 사용할 loginDto 객체를 뷰에 전달

        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            //클라이언트가 보낸 username, password를 loginDto에 자동 매핑
            //@Valid는 DTO 내부의 유효성 검사 어노테이션(@NotBlank 등)을 실행함
            @Valid @ModelAttribute() LoginDto loginDto,
            BindingResult bindingResult,    //유효성 검사 결과가 이 객체에 담김
            HttpSession httpSession,    	 //세션 객체를 인자로 받아서 사용함. 로그인 성공 시 이 안에 사용자 정보를 저장하여 로그인 상태를 유지
            Model model
    ) {
        if (bindingResult.hasErrors()) {	//유효성 검사 실패 시 처리
            return "login";
        }

        try {
            //DB에서 username으로 사용자 정보 검색
            User user = userRepository.findByUsername(loginDto.getUsername());

            //사용자가 입력한 비밀번호와 DB에 저장된 비밀번호를 비교
            if(!user.getPassword().equals(loginDto.getPassword())) {

                //login.html에서 ${error}로 출력
                model.addAttribute("error", "비밀번호가 올바르지 않습니다.");

                return "login";
            }

            httpSession.setAttribute("user", user);		//로그인한 사용자 정보를 세션에 저장
            System.out.println("로그인성공");
            return "redirect:/todos";

        } catch (Exception e) {

            //login.html에서 ${error}로 출력
            model.addAttribute("error", "존재하지 않는 사용자입니다.");

            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();	//현재 로그인된 사용자의 세션을 제거 → 로그인 해제됨

        return "redirect:/login";
    }
}