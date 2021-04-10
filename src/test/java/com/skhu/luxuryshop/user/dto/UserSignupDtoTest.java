package com.skhu.luxuryshop.user.dto;

import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.SignupPasswordUnmatchedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserSignupDtoTest {
    UserEntity user;
    UserSignupDto normalUserSignup;
    UserSignupDto unmatchedPwdUserSignup;

    @BeforeEach
    void prepare() {
        normalUserSignup = new UserSignupDto("test123@gmail.com", "password", "password", "홍길동");
        unmatchedPwdUserSignup = new UserSignupDto("test123@gmail.com", "password", "wrongPwd", "홍길동");
    }

    @Test
    void test_toUserEntity() {
        user = normalUserSignup.toUserEntity();

        assertEquals(user.getEmail(), normalUserSignup.getEmail());
        assertEquals(user.getPassword(), normalUserSignup.getPassword());
        assertEquals(user.getNickname(), normalUserSignup.getNickname());

        assertThrows(SignupPasswordUnmatchedException.class, () -> {
            unmatchedPwdUserSignup.toUserEntity();
        });
    }

    @Test
    void test_isSamePassword() {
        normalUserSignup.validateSamePassword(normalUserSignup.getPassword(), normalUserSignup.getPasswordCheck());
        assertThrows(SignupPasswordUnmatchedException.class, () -> {
            normalUserSignup.validateSamePassword(unmatchedPwdUserSignup.getPassword(), unmatchedPwdUserSignup.getPasswordCheck());
        });
    }
}