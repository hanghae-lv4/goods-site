package com.hanghae.spartagoods.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {

    @Email(message="올바른 이메일 형식이 아닙니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$",
            message="비밀번호는 최소 8자 이상, 15자 이하, 알파벳 대소문자, 숫자, 특수문자로 구성되어야 합니다.")
    private String password;

    private Character gender;
    private String phone;
    private String address;

    private boolean admin = false;

}
