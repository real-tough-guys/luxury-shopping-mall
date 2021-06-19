package com.skhu.luxuryshop.user.dto;

import com.skhu.luxuryshop.user.entity.Authority;
import com.skhu.luxuryshop.user.entity.UserAuthority;
import com.skhu.luxuryshop.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class UserResponseDtoTest {
    private UserEntity user;
    private UserResponseDto userResponseDto;
    private Authority authority = Authority.builder()
            .authorityName("ROLE_USER")
            .build();

    private UserAuthority userAuthority = UserAuthority.builder()
            .user(user)
            .auth(authority)
            .build();

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .id(999L)
                .email("test123@gmail.com")
                .password("password")
                .nickname("홍길동")
                .build();
    }

    @DisplayName("from_UserEntity")
    @Test
    void test_from() {
        userResponseDto = UserResponseDto.from(user);

        assertThat(user.getId()).isEqualTo(userResponseDto.getId());
        assertThat(user.getEmail()).isEqualTo(userResponseDto.getEmail());
        assertThat(user.getNickname()).isEqualTo(userResponseDto.getNickname());
    }
}