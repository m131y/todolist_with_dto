package com.my131.to_do_list_with_do.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
    @NotBlank(message = "아이디를 입력하세요") //공백시 에러메시지
    @Size(min=3, max=10, message = "아이디는 3~10자여야 합니다") //길이 제한 지정
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min=6, max=20, message = "비밀번호는 6~20자여야 합니다")
    private String password;
}