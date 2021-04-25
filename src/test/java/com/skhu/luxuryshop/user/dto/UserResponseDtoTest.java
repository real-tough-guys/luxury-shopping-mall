package com.skhu.luxuryshop.user.dto;

import com.skhu.luxuryshop.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserResponseDtoTest {
    private UserEntity user;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        user = new UserEntity(999L, "test123@gmail.com", "password", "홍길동");
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