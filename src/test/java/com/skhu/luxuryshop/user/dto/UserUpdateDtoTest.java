package com.skhu.luxuryshop.user.dto;

import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.UnmatchedPasswordCheckException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserUpdateDtoTest {
    private UserEntity user;
    private UserUpdateDto normalUserUpdate;
    private UserUpdateDto unmatchedPwdUserUpdate;

    @BeforeEach
    void setUp() {
        normalUserUpdate = new UserUpdateDto(1L,"test123@gmail.com", "password", "password", "홍길동");
        unmatchedPwdUserUpdate = new UserUpdateDto(2L,"test123@gmail.com", "password", "wrongPwd", "홍길동");
    }

    @DisplayName("toUserEntity_정상유저일 경우 ")
    @Test
    void test_toUserEntity_normalUserSignup() {
        user = normalUserUpdate.toUserEntity();

        assertThat(normalUserUpdate.getId()).isEqualTo(user.getId());
        assertThat(normalUserUpdate.getEmail()).isEqualTo(user.getEmail());
        assertThat(normalUserUpdate.getPassword()).isEqualTo(user.getPassword());
        assertThat(normalUserUpdate.getNickname()).isEqualTo(user.getNickname());
    }

    @DisplayName("toUserEntity_비밀번호 불일치 유저일 경우 throw UnmatchedPasswordCheckException")
    @Test
    void test_toUserEntity_unmatchedPwdUserSignup() {
        assertThatThrownBy(() -> {
            unmatchedPwdUserUpdate.toUserEntity();
        }).isInstanceOf(UnmatchedPasswordCheckException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("validateSamePassword_정상유저인 경우")
    @Test
    void test_validateSamePassword_normalUserSignup() {
        normalUserUpdate.validateSamePassword(normalUserUpdate.getPassword(), normalUserUpdate.getPasswordCheck());
    }

    @DisplayName("validateSamePassword_비밀번호 불일치 유저인 경우 throw UnmatchedPasswordCheckException")
    @Test
    void test_validateSamePassword_unmatchedPwdUserSignup() {
        assertThatThrownBy(() -> {
            unmatchedPwdUserUpdate.validateSamePassword(unmatchedPwdUserUpdate.getPassword(), unmatchedPwdUserUpdate.getPasswordCheck());
        }).isInstanceOf(UnmatchedPasswordCheckException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }
}