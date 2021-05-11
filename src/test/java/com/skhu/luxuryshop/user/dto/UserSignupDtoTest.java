package com.skhu.luxuryshop.user.dto;

import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.UnmatchedPasswordCheckException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserSignupDtoTest {
    private UserEntity user;
    private UserSignupDto normalUserSignup;
    private UserSignupDto unmatchedPwdUserSignup;

    @BeforeEach
    void setUp() {
        normalUserSignup = new UserSignupDto("test123@gmail.com", "password", "password", "홍길동");
        unmatchedPwdUserSignup = new UserSignupDto("test123@gmail.com", "password", "wrongPwd", "홍길동");
    }

    @DisplayName("toUserEntity_정상유저일 경우 ")
    @Test
    void test_toUserEntity_normalUserSignup() {
        user = normalUserSignup.toUserEntity();

        assertThat(normalUserSignup.getEmail()).isEqualTo(user.getEmail());
        assertThat(normalUserSignup.getPassword()).isEqualTo(user.getPassword());
        assertThat(normalUserSignup.getNickname()).isEqualTo(user.getNickname());
    }

    @DisplayName("toUserEntity_비밀번호 불일치 유저일 경우 throw UnmatchedPasswordCheckException")
    @Test
    void test_toUserEntity_unmatchedPwdUserSignup() {
        assertThatThrownBy(() -> {
            unmatchedPwdUserSignup.toUserEntity();
        }).isInstanceOf(UnmatchedPasswordCheckException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("validateSamePassword_정상유저인 경우")
    @Test
    void test_validateSamePassword_normalUserSignup() {
        normalUserSignup.validateSamePassword(normalUserSignup.getPassword(), normalUserSignup.getPasswordCheck());
    }

    @DisplayName("validateSamePassword_비밀번호 불일치 유저인 경우 throw UnmatchedPasswordCheckException")
    @Test
    void test_validateSamePassword_unmatchedPwdUserSignup() {
        assertThatThrownBy(() -> {
            normalUserSignup.validateSamePassword(unmatchedPwdUserSignup.getPassword(), unmatchedPwdUserSignup.getPasswordCheck());
        }).isInstanceOf(UnmatchedPasswordCheckException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }
}