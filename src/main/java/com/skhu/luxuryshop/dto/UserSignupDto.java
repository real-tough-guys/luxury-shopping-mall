package com.skhu.luxuryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignupDto {
    private String email;
    private String password;
    private String passwordCheck;
    private String nickname;
}
