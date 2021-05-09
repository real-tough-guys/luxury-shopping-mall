package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.dto.UserUpdateDto;
import com.skhu.luxuryshop.user.entity.Authority;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.exception.NoUserFoundException;
import com.skhu.luxuryshop.user.exception.UnmatchedPasswordCheckException;
import com.skhu.luxuryshop.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class UserManagementServiceTest {
    @Autowired
    private UserManagementService userManagementService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserSignupDto existentUser1;
    private Authority authority = Authority.builder()
            .authorityName("ROLE_USER")
            .build();
    private Long existentUserId1;

    @BeforeEach
    void setUp() {
        existentUser1 = new UserSignupDto("test1@gmail.com", "password", "password", "홍길동");

        UserEntity existentUserEntity1 = UserEntity.builder()
                .email(existentUser1.getEmail())
                .password(passwordEncoder.encode(existentUser1.getPassword()))
                .nickname(existentUser1.getNickname())
                .authorities(Collections.singleton(authority))
                .build();
        existentUserId1 = userRepository.save(existentUserEntity1).getId();
    }

    @DisplayName("findById_존재하는 유저의 id인 경우")
    @Test
    void test_findById_existentUserId() {
        UserResponseDto userResponseDto = userManagementService.findById(existentUserId1);

        assertThat(userResponseDto.getEmail()).isEqualTo(existentUser1.getEmail());
        assertThat(userResponseDto.getNickname()).isEqualTo(existentUser1.getNickname());
    }

    @DisplayName("findById_존재하지 않는 유저의 id인 경우 throw NoUserFoundException")
    @Test
    void test_findById_nonExistentUserId() {
        assertThatThrownBy(() -> {
            userManagementService.findById(999L);
        }).isInstanceOf(NoUserFoundException.class)
                .hasMessageContaining("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("findByLoginUser_로그인된 유저인 경우")
    @Test
    void test_findByLoginUser_loginUser() {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(existentUser1.getEmail(), existentUser1.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserResponseDto userResponseDto = userManagementService.findByLoginUser();

        assertThat(userResponseDto.getEmail()).isEqualTo(existentUser1.getEmail());
        assertThat(userResponseDto.getNickname()).isEqualTo(existentUser1.getNickname());
    }

    @DisplayName("findByLoginUser_로그아웃된 경우 throw NoUserFoundException")
    @Test
    void test_findByLoginUser_logoutUser() {
        assertThatThrownBy(() -> {
            userManagementService.findByLoginUser();
        }).isInstanceOf(NoUserFoundException.class)
                .hasMessageContaining("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("deleteById_존재하는 유저의 id인 경우")
    @Test
    void test_deleteById_existentUserId() {
        userManagementService.deleteById(existentUserId1);

        assertThat(false).isEqualTo(userRepository.existsById(existentUserId1));
    }

    @DisplayName("deleteById_존재하지 않는 유저의 id인 경우 throw NoUserFoundException")
    @Test
    void test_deleteById_nonExistentUserId() {
        assertThatThrownBy(() -> {
            userManagementService.deleteById(999L);
        }).isInstanceOf(NoUserFoundException.class)
                .hasMessageContaining("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("deleteByLoginUser_로그인된 유저인 경우")
    @Test
    void test_deleteByLoginUser_loginUser() {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(existentUser1.getEmail(), existentUser1.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        userManagementService.deleteByLoginUser();

        assertThat(false).isEqualTo(userRepository.existsById(existentUserId1));
    }

    @DisplayName("deleteByLoginUser_로그아웃된 경우 throw NoUserFoundException")
    @Test
    void test_deleteByLoginUser_logoutUser() {
        assertThatThrownBy(() -> {
            userManagementService.deleteByLoginUser();
        }).isInstanceOf(NoUserFoundException.class)
                .hasMessageContaining("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("update_로그인된 유저인 경우")
    @Test
    void test_update_loginUser() {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(existentUser1.getEmail(), existentUser1.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserUpdateDto existentUserUpdate = new UserUpdateDto(existentUserId1, "test1@gmail.com", "changedPwd", "changedPwd", "김북남");

        userManagementService.update(existentUserUpdate);
        UserResponseDto userResponseDto = userManagementService.findById(existentUserUpdate.getId());

        assertThat(userResponseDto.getEmail()).isEqualTo(existentUserUpdate.getEmail());
        assertThat(userResponseDto.getNickname()).isEqualTo(existentUserUpdate.getNickname());
    }

    @DisplayName("update_로그아웃된 경우 throw NoUserFoundException")
    @Test
    void test_update_nonExistentUser() {
        UserUpdateDto nonExistentUserUpdate = new UserUpdateDto(existentUserId1, "test1@gmail.com", "changedPwd", "changedPwd", "김북남");

        assertThatThrownBy(() -> {
            userManagementService.update(nonExistentUserUpdate);
        }).isInstanceOf(NoUserFoundException.class)
                .hasMessageContaining("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("update_비밀번호 불일치 유저인 경우 throw UnmatchedPasswordCheckException")
    @Test
    void test_update_unmatchedPwdUser() {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(existentUser1.getEmail(), existentUser1.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserUpdateDto unmatchedPwdUserUpdate = new UserUpdateDto(existentUserId1, "test1@gmail.com", "changedPwd", "wrongPwd", "김북남");

        assertThatThrownBy(() -> {
            userManagementService.update(unmatchedPwdUserUpdate);
        }).isInstanceOf(UnmatchedPasswordCheckException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("update_이메일 중복 유저인 경우 throw DuplicatedEmailException")
    @Test
    void test_update_duplicatedEmailUser() {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(existentUser1.getEmail(), existentUser1.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserUpdateDto duplicatedEmailUserUpdate = new UserUpdateDto(existentUserId1, existentUser1.getEmail(), "changedPwd", "changedPwd", "김북남");

        assertThatThrownBy(() -> {
            userManagementService.update(duplicatedEmailUserUpdate);
        }).isInstanceOf(DuplicatedEmailException.class)
                .hasMessageContaining("중복된 이메일입니다.");
    }

    @DisplayName("findAll_모든 유저 탐색")
    @Test
    void test_findAll() {
        List<UserResponseDto> users = userManagementService.findAll();
        users.stream().sorted(Comparator.comparing((UserResponseDto userResponseDto) -> userResponseDto.getId()));

        assertThat(users.size()).isEqualTo(3);
        assertThat(users.get(2).getEmail()).isEqualTo(existentUser1.getEmail());
        assertThat(users.get(2).getNickname()).isEqualTo(existentUser1.getNickname());
    }
}