package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.dto.UserUpdateDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class UserManagementServiceTest {
    @Autowired
    private UserManagementService userManagementService;
    @Autowired
    private UserRepository userRepository;

    private UserSignupDto existentUser1;
    private UserSignupDto existentUser2;
    private Long existentUserId1;

    @BeforeEach
    void setUp() {
        existentUser1 = new UserSignupDto("test1@gmail.com", "password", "password", "홍길동");
        existentUser2 = new UserSignupDto("test2@gmail.com", "password", "password", "홍길서");
        existentUserId1 = userRepository.save(existentUser1.toUserEntity()).getId();
        userRepository.save(existentUser2.toUserEntity());
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

    @DisplayName("update_존재하는 유저인 경우")
    @Test
    void test_update_existentUser() {
        UserUpdateDto existentUserUpdate = new UserUpdateDto(existentUserId1, "test1@gmail.com", "changedPwd", "changedPwd", "김북남");

        userManagementService.update(existentUserUpdate);
        UserResponseDto userResponseDto = userManagementService.findById(existentUserUpdate.getId());

        assertThat(userResponseDto.getEmail()).isEqualTo(existentUserUpdate.getEmail());
        assertThat(userResponseDto.getNickname()).isEqualTo(existentUserUpdate.getNickname());
    }

    @DisplayName("update_존재하지 않는 유저인 경우 throw NoUserFoundException")
    @Test
    void test_update_nonExistentUser() {
        UserUpdateDto nonExistentUserUpdate = new UserUpdateDto(999L, "test1@gmail.com", "changedPwd", "changedPwd", "김북남");

        assertThatThrownBy(() -> {
            userManagementService.update(nonExistentUserUpdate);
        }).isInstanceOf(NoUserFoundException.class)
                .hasMessageContaining("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("update_비밀번호 불일치 유저인 경우 throw UnmatchedPasswordCheckException")
    @Test
    void test_update_unmatchedPwdUser() {
        UserUpdateDto unmatchedPwdUserUpdate = new UserUpdateDto(existentUserId1, "test1@gmail.com", "changedPwd", "wrongPwd", "김북남");

        assertThatThrownBy(() -> {
            userManagementService.update(unmatchedPwdUserUpdate);
        }).isInstanceOf(UnmatchedPasswordCheckException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("findAll_모든 유저 탐색")
    @Test
    void test_findAll() {
        List<UserResponseDto> users = userManagementService.findAll();
        users.stream().sorted(Comparator.comparing((UserResponseDto userResponseDto) -> userResponseDto.getId()));

        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0).getEmail()).isEqualTo(existentUser1.getEmail());
        assertThat(users.get(0).getNickname()).isEqualTo(existentUser1.getNickname());
    }
}