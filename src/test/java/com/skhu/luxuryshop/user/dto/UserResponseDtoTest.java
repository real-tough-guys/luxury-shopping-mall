package com.skhu.luxuryshop.user.dto;

import com.skhu.luxuryshop.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserResponseDtoTest {
    UserEntity user;
    UserResponseDto userResponseDto;

    @BeforeEach
    void prepare() {
        user = new UserEntity(999L, "test123@gmail.com", "password", "홍길동");
    }

    @Test
    void test_from() {
        userResponseDto = UserResponseDto.from(user);

        assertEquals(user.getId(), userResponseDto.getId());
        assertEquals(user.getEmail(), userResponseDto.getEmail());
        assertEquals(user.getNickname(), userResponseDto.getNickname());
    }
}