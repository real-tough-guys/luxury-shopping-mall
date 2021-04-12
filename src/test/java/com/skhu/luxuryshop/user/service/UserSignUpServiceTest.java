package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.exception.SignupPasswordUnmatchedException;
import com.skhu.luxuryshop.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserSignUpServiceTest {
    @Autowired
    UserSignupService userSignupService;
    @Autowired
    UserRepository userRepository;

    UserSignupDto existsUser;
    UserSignupDto normalUserSignup;
    UserSignupDto emailDuplicatedUserSignup;
    UserSignupDto unmatchedPwdUserSignup;

    @BeforeEach
    void prepareForAll() {
        existsUser = new UserSignupDto(findUnDuplicatedUserEmail(), "password", "password", "홍길동");
        userRepository.save(existsUser.toUserEntity());
        normalUserSignup = new UserSignupDto(findUnDuplicatedUserEmail(), "password", "password", "홍길동");
        emailDuplicatedUserSignup = new UserSignupDto(existsUser.getEmail(), "password", "password", "홍길동");
    }

    @Test
    void test_save() {
        UserResponseDto normalUserResponse = userSignupService.save(normalUserSignup);
        assertEquals(normalUserResponse.getEmail(), normalUserSignup.getEmail());

        assertThrows(DuplicatedEmailException.class, () -> {
            userSignupService.save(emailDuplicatedUserSignup);
        });
        unmatchedPwdUserSignup = new UserSignupDto(findUnDuplicatedUserEmail(), "password", "wrongPwd", "홍길동");
        assertThrows(SignupPasswordUnmatchedException.class, () -> {
            userSignupService.save(unmatchedPwdUserSignup);
        });
    }

    @Test
    void test_isDuplicatedEmail() {
        assertThrows(DuplicatedEmailException.class, () -> {
            userSignupService.validateDuplicatedEmail(emailDuplicatedUserSignup.getEmail());
        });
        userSignupService.validateDuplicatedEmail(normalUserSignup.getEmail());
    }

    String findUnDuplicatedUserEmail() {
        String unDuplicatedUserEmail;
        int subString = 1;
        while (subString < 99999) {
            unDuplicatedUserEmail = subString + "test123@gmail.co.kr";
            if (!userRepository.existsByEmail(unDuplicatedUserEmail)) {
                return unDuplicatedUserEmail;
            }
            subString++;
        }
        return null;
    }
}