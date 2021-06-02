package com.skhu.luxuryshop.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserTokenDto {
    private Long id;
    private String token;
}