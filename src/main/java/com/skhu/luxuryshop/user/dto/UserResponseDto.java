package com.skhu.luxuryshop.user.dto;

import com.skhu.luxuryshop.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;

    public static UserResponseDto from(UserEntity user) {
        return new UserResponseDto(user.getId(), user.getEmail(), user.getNickname());
    }
}