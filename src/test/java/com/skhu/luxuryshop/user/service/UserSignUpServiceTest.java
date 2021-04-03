package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.exception.SignupPasswordUnmatchedException;
import com.skhu.luxuryshop.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserSignUpServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserSignupService userSignupService;

    UserEntity user;
    Optional<UserEntity> wrappedUser;
    Optional<UserEntity> wrappedEmptyUser;

    UserSignupDto normalUserSignup;
    UserSignupDto emailDuplicatedUserSignup;
    UserSignupDto unmatchedPwdUserSignup;

    @BeforeEach
    void prepare() {
        user = new UserEntity(999, "test123@gmail.co.kr", "password", "홍길홍");
        normalUserSignup = new UserSignupDto("unDuplicatied@gmail.co.kr", "password", "password", "홍길동");
        emailDuplicatedUserSignup = new UserSignupDto("test123@gmail.co.kr", "password", "password", "홍길동");
        unmatchedPwdUserSignup = new UserSignupDto("unDuplcatied1@gmail.co.kr", "password", "wrongPwd", "홍길동");

        wrappedUser = Optional.of(user);
        wrappedEmptyUser = Optional.empty();
    }

    //회원생성 테스트
    @Test
    void test_save() {
        when(userRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(wrappedEmptyUser);
        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(wrappedUser);

        userSignupService.save(normalUserSignup);
        Mockito.verify(userRepository).save(ArgumentMatchers.any(UserEntity.class));

        assertThrows(DuplicatedEmailException.class, () -> {
            userSignupService.save(emailDuplicatedUserSignup);
        });
        assertThrows(SignupPasswordUnmatchedException.class, () -> {
            userSignupService.save(unmatchedPwdUserSignup);
        });
    }

    //이메일 중복여부 테스트
    @Test
    void test_isDuplicatedEmail() {
        when(userRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(wrappedEmptyUser);
        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(wrappedUser);

        assertThrows(DuplicatedEmailException.class, () -> {
            userSignupService.isDuplicatedEmail(user.getEmail());
        });
        assertFalse(userSignupService.isDuplicatedEmail(normalUserSignup.getEmail()));
    }
}