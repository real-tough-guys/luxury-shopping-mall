package com.skhu.luxuryshop.user.dto;

import com.skhu.luxuryshop.user.entity.UserAuthority;
import com.skhu.luxuryshop.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String address;
    private List<UserAuthority> authorities;

    public static UserResponseDto from(UserEntity user) {
        return new UserResponseDto(user.getId(), user.getEmail(), user.getNickname(), user.getAddress() ,user.getAuthorities());
    }
}