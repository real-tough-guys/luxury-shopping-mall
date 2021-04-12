package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.exception.SignupPasswordUnmatchedException;
import com.skhu.luxuryshop.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class UserSignUpServiceTest {
    @Autowired
    private UserSignupService userSignupService;
    @Autowired
    private UserRepository userRepository;

    private UserSignupDto existsUser;
    private UserSignupDto normalUserSignup;
    private UserSignupDto duplicatedEmailUserSignup;

    @BeforeEach
    void setUp() {
        existsUser = new UserSignupDto("test123@gmail.com", "password", "password", "홍길동");
        userRepository.save(existsUser.toUserEntity());
        normalUserSignup = new UserSignupDto("unDuplicatedEmail@gmail.com", "password", "password", "홍길동");
        duplicatedEmailUserSignup = new UserSignupDto(existsUser.getEmail(), "password", "password", "홍길동");
    }

    @DisplayName("save_정상유저인 경우")
    @Test
    void test_save_normalUserSignup() {
        UserResponseDto normalUserResponse = userSignupService.save(normalUserSignup);
        assertThat(normalUserSignup.getEmail()).isEqualTo(normalUserResponse.getEmail());
    }

    @DisplayName("save_중복된 이메일을 가진 유저인 경우 throw DuplicatedEmailException")
    @Test
    void test_save_duplicatedEmailUserSignup() {
        assertThatThrownBy(() -> {
            userSignupService.save(duplicatedEmailUserSignup);
        }).isInstanceOf(DuplicatedEmailException.class)
                .hasMessageContaining(("중복된 이메일"));
    }

    @DisplayName("save_비밀번호 불일치 유저인 경우 throw SignupPasswordUnmatchedException")
    @Test
    void test_save_unmathcedPwdUserSignup() {
        UserSignupDto unmatchedPwdUserSignup = new UserSignupDto("unDuplicatedEmail1@gmail.com", "password", "wrongpwd", "홍길동");
        assertThatThrownBy(() -> {
            userSignupService.save(unmatchedPwdUserSignup);
        }).isInstanceOf(SignupPasswordUnmatchedException.class)
                .hasMessageContaining(("비밀번호 불일치"));
    }

    @DisplayName("validateDuplicatedEmail_중복되지 않은 이메일인 경우")
    @Test
    void test_validateDuplicatedEmail_normalEmail() {
        userSignupService.validateDuplicatedEmail(normalUserSignup.getEmail());
    }

    @DisplayName("validateDuplicatedEmail_중복된 이메일인 경우 throw DuplicatedEmailException")
    @Test
    void test_validateDuplicatedEmail_duplicatedEmail() {
        assertThatThrownBy(() -> {
            userSignupService.validateDuplicatedEmail(duplicatedEmailUserSignup.getEmail());
        }).isInstanceOf(DuplicatedEmailException.class)
                .hasMessageContaining("중복된 이메일");
    }
}