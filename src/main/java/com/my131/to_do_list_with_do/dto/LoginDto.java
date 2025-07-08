package com.my131.to_do_list_with_do.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @NotBlank(message = "아이디를 입력하세요") //공백시 에러메시지
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;
}
