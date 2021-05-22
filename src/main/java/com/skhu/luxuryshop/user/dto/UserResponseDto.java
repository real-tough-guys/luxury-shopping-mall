package com.skhu.luxuryshop.user.dto;

import com.skhu.luxuryshop.user.entity.Authority;
import com.skhu.luxuryshop.user.entity.UserAuthority;
import com.skhu.luxuryshop.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private List<UserAuthority> authorities;

    public static UserResponseDto from(UserEntity user) {
        return new UserResponseDto(user.getId(), user.getEmail(), user.getNickname(), user.getAuthorities());
    }
}