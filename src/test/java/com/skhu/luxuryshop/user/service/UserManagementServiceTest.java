package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.dto.UserLoginDto;
import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.dto.UserUpdateDto;
import com.skhu.luxuryshop.user.encoder.BCryptPasswordEncoder;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserManagementServiceTest {
    @Autowired
    private UserManagementService userManagementService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private UserSignupDto existentUser;
    private Long existentUserId;

    @BeforeEach
    void setUp() {
        existentUser = new UserSignupDto("test1@gmail.com", "password", "password", "홍길동");

        UserEntity existentUserEntity1 = UserEntity.builder()
                .email(existentUser.getEmail())
                .password(passwordEncoder.encrypt(existentUser.getPassword()))
                .nickname(existentUser.getNickname())
                .build();

        existentUserId = userRepository.save(existentUserEntity1).getId();
    }

    @DisplayName("findById_존재하는 유저의 id인 경우")
    @Test
    void test_findById_existentUserId() {
        UserResponseDto userResponseDto = userManagementService.findById(existentUserId);

        assertThat(userResponseDto.getEmail()).isEqualTo(existentUser.getEmail());
        assertThat(userResponseDto.getNickname()).isEqualTo(existentUser.getNickname());
    }

    @DisplayName("findById_존재하지 않는 유저의 id인 경우 throw NoUserFoundException")
    @Test
    void test_findById_nonExistentUserId() {
        assertThatThrownBy(() -> {
            userManagementService.findById(999L);
        }).isInstanceOf(NoUserFoundException.class)
                .hasMessageContaining("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("deleteById_존재하는 유저의 id인 경우")
    @Test
    void test_deleteById_existentUserId() {
        userManagementService.deleteById(existentUserId);

        assertThat(userRepository.existsById(existentUserId)).isFalse();
    }

    @DisplayName("deleteById_존재하지 않는 유저의 id인 경우 throw NoUserFoundException")
    @Test
    void test_deleteById_nonExistentUserId() {
        assertThatThrownBy(() -> {
            userManagementService.deleteById(999L);
        }).isInstanceOf(NoUserFoundException.class)
                .hasMessageContaining("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("update_정상 유저인 경우")
    @Test
    void test_update_normalUser() {
        UserUpdateDto normalUserUpdate = new UserUpdateDto(existentUserId, "test1@gmail.com", "changedPwd", "changedPwd", "김북남");

        userManagementService.update(normalUserUpdate);
        UserResponseDto updatedUser = userManagementService.findById(existentUserId);

        assertThat(updatedUser.getEmail()).isEqualTo(normalUserUpdate.getEmail());
        assertThat(updatedUser.getNickname()).isEqualTo(normalUserUpdate.getNickname());
    }

    @DisplayName("update_비밀번호 불일치 유저인 경우 throw UnmatchedPasswordCheckException")
    @Test
    void test_update_unmatchedPwdUser() {

        UserUpdateDto unmatchedPwdUserUpdate = new UserUpdateDto(existentUserId, "test1@gmail.com", "changedPwd", "wrongPwd", "김북남");

        assertThatThrownBy(() -> {
            userManagementService.update(unmatchedPwdUserUpdate);
        }).isInstanceOf(UnmatchedPasswordCheckException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("update_이메일 중복 유저인 경우 throw DuplicatedEmailException")
    @Test
    void test_update_duplicatedEmailUser() {
        UserEntity user = UserEntity.builder()
                .email("duplicatedEmail@gmail.com")
                .password("password")
                .nickname("홍길동")
                .build();
        userRepository.save(user);
        UserUpdateDto duplicatedEmailUserUpdate = new UserUpdateDto(existentUserId, user.getEmail(), "changedPwd", "changedPwd", "김북남");


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
        assertThat(users.get(2).getEmail()).isEqualTo(existentUser.getEmail());
        assertThat(users.get(2).getNickname()).isEqualTo(existentUser.getNickname());
    }

    @DisplayName("validateDuplicatedEmail_중복되지 않은 이메일인 경우")
    @Test
    void test_validateDuplicatedEmail_normalEmail() {
        userManagementService.validateDuplicatedEmail("unDuplicatedEmail@gmail.co.kr");
        assertThatNoException();
    }

    @DisplayName("validateDuplicatedEmail_중복된 이메일인 경우 throw DuplicatedEmailException")
    @Test
    void test_validateDuplicatedEmail_duplicatedEmail() {
        assertThatThrownBy(() -> {
            userManagementService.validateDuplicatedEmail(existentUser.getEmail());
        }).isInstanceOf(DuplicatedEmailException.class)
                .hasMessageContaining("중복된 이메일입니다.");
    }

    @DisplayName("login_정상 유저인 경우")
    @Test
    void test_login_normalUser() {
        UserLoginDto loginDto = new UserLoginDto(existentUser.getEmail(), existentUser.getPassword());
        UserEntity user = userManagementService.login(loginDto);

        assertThat(loginDto.getEmail()).isEqualTo(user.getEmail());
    }

    @DisplayName("login_존재하지 않는 유저인 경우 throw NoUserFoundException")
    @Test
    void test_login_noExistUser() {
        UserLoginDto loginDto = new UserLoginDto("unDuplicatedEmail@gmail.co.kr", existentUser.getPassword());

        assertThatThrownBy(() -> {
            userManagementService.login(loginDto);
        }).isInstanceOf(NoUserFoundException.class)
                .hasMessageContaining("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("login_비밀번호가 틀린 경우 throw UnMatchedPasswordCheckException")
    @Test
    void test_login_wrongPasswordUser() {
        UserLoginDto loginDto = new UserLoginDto(existentUser.getEmail(), "wrongPwd");

        assertThatThrownBy(() -> {
            userManagementService.login(loginDto);
        }).isInstanceOf(UnmatchedPasswordCheckException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }
}