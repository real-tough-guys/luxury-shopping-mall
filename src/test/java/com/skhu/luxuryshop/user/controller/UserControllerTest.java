package com.skhu.luxuryshop.user.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhu.luxuryshop.user.dto.UserLoginDto;
import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.dto.UserUpdateDto;
import com.skhu.luxuryshop.user.entity.Authority;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.exception.NoUserFoundException;
import com.skhu.luxuryshop.user.exception.UnmatchedPasswordCheckException;
import com.skhu.luxuryshop.user.jwt.TokenProvider;
import com.skhu.luxuryshop.user.service.UserManagementService;
import com.skhu.luxuryshop.user.service.UserSignupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureWebMvc
@WithMockUser(username = "test1@gmail.com", roles = {"USER", "ADMIN"})
public class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @MockBean
    private UserSignupService userSignupService;
    @MockBean
    private UserManagementService userManagementService;
    @MockBean
    private TokenProvider tokenProvider;

    private UserEntity existentUser;
    private UserSignupDto normalUserSignup;
    private UserSignupDto duplicatedEmailUserSignup;
    private UserSignupDto unmatchedPwdUserSignup;
    private Authority authority = Authority.builder()
            .authorityName("ROLE_USER")
            .build();

    @BeforeEach
    void setUp() {
        existentUser = new UserEntity(1L, "test1@gmail.com", "password", "nick", Collections.singleton(authority));
        normalUserSignup = new UserSignupDto("test123@gmail.co.kr", "password", "password", "홍길동");
        duplicatedEmailUserSignup = new UserSignupDto("test123@gmail.com", "password", "password", "홍길동");
    }

    @DisplayName("isDuplicatedEmail_중복되지 않은 이메일 요청인 경우 Ok")
    @Test
    void test_isDuplicatedEmail_unDuplicatedEmail() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/users/emails/" + normalUserSignup.getEmail() + "/exists"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("중복되지 않은 이메일입니다.");
    }

    @DisplayName("isDuplicatedEmail_중복된 이메일 요청인 경우 BadRequest")
    @Test
    void test_isDuplicatedEmail_duplicatedEmail() throws Exception {
        doThrow(new DuplicatedEmailException())
                .when(userSignupService)
                .validateDuplicatedEmail(duplicatedEmailUserSignup.getEmail());

        MvcResult mvcResult = mockMvc.perform(get("/api/users/emails/" + duplicatedEmailUserSignup.getEmail() + "/exists"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("중복된 이메일입니다.");
    }

    @DisplayName("signUp_정상 유저인 경우 isCreated")
    @Test
    void test_signUp_normalUser() throws Exception {
        UserResponseDto normalUserResponse = new UserResponseDto(1L, normalUserSignup.getEmail(), normalUserSignup.getNickname(), Collections.singleton(authority));
        when(userSignupService.save(ArgumentMatchers.any(UserSignupDto.class)))
                .thenReturn(normalUserResponse);

        MvcResult mvcResult = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(normalUserSignup)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        assertThat(location).isEqualTo("/" + normalUserResponse.getId());
    }

    @DisplayName("signUp_중복된 이메일을 가진 유저인 경우 BadRequest")
    @Test
    void test_signUp_duplicatedEmailUser() throws Exception {
        when(userSignupService.save(ArgumentMatchers.any(UserSignupDto.class)))
                .thenThrow(new DuplicatedEmailException());

        MvcResult mvcResult = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicatedEmailUserSignup)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("중복된 이메일입니다.");
    }

    @DisplayName("signUp_비밀번호 불일치 유저인 경우 BadRequest")
    @Test
    void test_signUp_unmatchedPasswordUser() throws Exception {
        unmatchedPwdUserSignup = new UserSignupDto("test123@gmail.com", "password", "wrongpwd", "홍길동");

        when(userSignupService.save(ArgumentMatchers.any(UserSignupDto.class)))
                .thenThrow(new UnmatchedPasswordCheckException());

        MvcResult mvcResult = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unmatchedPwdUserSignup)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("details(ROLE_ADMIN)_존재하는 유저의 id인 경우")
    @Test
    void test_details_existentUserId() throws Exception {
        doReturn(UserResponseDto.from(existentUser))
                .when(userManagementService)
                .findById(existentUser.getId());

        MvcResult mvcResult = mockMvc.perform(get("/api/users/" + existentUser.getId() + "/details"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        UserResponseDto userResponseDto = objectMapper.readValue(body, UserResponseDto.class);

        assertThat(userResponseDto.getEmail()).isEqualTo(existentUser.getEmail());
        assertThat(userResponseDto.getNickname()).isEqualTo(existentUser.getNickname());
    }

    @DisplayName("details(ROLE_ADMIN)_존재하지 않는 유저의 id인 경우 throw NoUserFoundException")
    @Test
    void test_details_nonExistentUserId() throws Exception {
        doThrow(new NoUserFoundException())
                .when(userManagementService)
                .findById(ArgumentMatchers.any());

        MvcResult mvcResult = mockMvc.perform(get("/api/users/" + 999L + "/details"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("details(ROLE_USER)_로그인 되어있을 경우")
    @Test
    void test_myDetails_loginUser() throws Exception {
        doReturn(UserResponseDto.from(existentUser))
                .when(userManagementService)
                .findByLoginUser();

        MvcResult mvcResult = mockMvc.perform(get("/api/users/details"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        UserResponseDto userResponseDto = objectMapper.readValue(body, UserResponseDto.class);

        assertThat(userResponseDto.getEmail()).isEqualTo(existentUser.getEmail());
        assertThat(userResponseDto.getNickname()).isEqualTo(existentUser.getNickname());
    }

    @DisplayName("details(ROLE_USER)_로그아웃 되어있을 경우 throw AccessDeniedException")
    @Test
    @WithMockUser(roles = "")
    void test_myDetails_logoutUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/users/details"))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("Access is denied");
    }

    @DisplayName("delete(ROLE_ADMIN)_존재하는 유저의 id인 경우")
    @Test
    void test_delete_existentUserId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/users/" + existentUser.getId() + "/delete"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("계정이 삭제되었습니다.");
    }

    @DisplayName("delete(ROLE_ADMIN)_존재하지 않는 유저의 id인 경우")
    @Test
    void test_delete_nonExistentUserId() throws Exception {

        doThrow(new NoUserFoundException())
                .when(userManagementService)
                .deleteById(999L);

        MvcResult mvcResult = mockMvc.perform(post("/api/users/" + 999 + "/delete"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("delete(ROLE_USER)_로그인 되어있을 경우")
    @Test
    void test_delete_loginUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/users/delete"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("계정이 삭제되었습니다.");
    }

    @DisplayName("delete(ROLE_USER)_로그아웃 되어있을 경우 throw AccessDeniedException")
    @Test
    @WithMockUser(roles = "")
    void test_delete_logoutUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/users/delete"))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("Access is denied");
    }

    @DisplayName("update(ROLE_USER)_존재하는 유저인 경우")
    @Test
    void test_update_existentUser() throws Exception {
        UserUpdateDto existentUserUpdate = new UserUpdateDto(this.existentUser.getId(), this.existentUser.getEmail(), "changePwd", "changePwd", "newNick");

        MvcResult mvcResult = mockMvc.perform(post("/api/users/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existentUserUpdate)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("정보를 변경했습니다. 다시 로그인하세요.");
    }

    @DisplayName("update(ROLE_USER)_존재하지 않는 유저인 경우 throw NoUserFoundException")
    @Test
    void test_update_nonExistentUser() throws Exception {
        UserUpdateDto nonExistentUserUpdate = new UserUpdateDto(999L, "email@gmail.com", "changePwd", "changePwd", "newNick");

        doThrow(new NoUserFoundException())
                .when(userManagementService)
                .update(ArgumentMatchers.any(UserUpdateDto.class));

        MvcResult mvcResult = mockMvc.perform(post("/api/users/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nonExistentUserUpdate)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("update(ROLE_USER)_비밀번호가 불일치인 경우 throw UnmatchedPasswordCheckException")
    @Test
    void test_update_unmatchedPasswordUser() throws Exception {
        UserUpdateDto unmatchedPwdUserUpdate = new UserUpdateDto(existentUser.getId(), existentUser.getEmail(), "changePwd", "wrongPwd", "newNick");

        doThrow(new UnmatchedPasswordCheckException())
                .when(userManagementService)
                .update(ArgumentMatchers.any(UserUpdateDto.class));

        MvcResult mvcResult = mockMvc.perform(post("/api/users/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unmatchedPwdUserUpdate)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("update(ROLE_USER)_로그아웃 되어있을 경우 throw AccessDeniedException")
    @Test
    @WithMockUser(roles = "")
    void test_update_logoutUser() throws Exception {
        UserUpdateDto unmatchedPwdUserUpdate = new UserUpdateDto(existentUser.getId(), existentUser.getEmail(), "changePwd", "wrongPwd", "newNick");

        MvcResult mvcResult = mockMvc.perform(post("/api/users/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unmatchedPwdUserUpdate)))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("Access is denied");
    }

    @DisplayName("findAll(ROLE_ADMIN)_모든 유저 조회")
    @Test
    void test_findAll() throws Exception {
        List<UserResponseDto> users = new ArrayList<>();
        UserResponseDto userResponseDto1 = new UserResponseDto(1L, "test1@gmail.com", "nick", Collections.singleton(authority));
        UserResponseDto userResponseDto2 = new UserResponseDto(2L, "test2@gmail.com", "nick", Collections.singleton(authority));
        users.add(userResponseDto1);
        users.add(userResponseDto2);

        doReturn(users)
                .when(userManagementService)
                .findAll();

        MvcResult mvcResult = mockMvc.perform(get("/api/users/list"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        List<UserResponseDto> convertedUsers = objectMapper.readValue(body, new TypeReference<List<UserResponseDto>>() {
        });
        assertThat(convertedUsers.size()).isEqualTo(users.size());
        assertThat(convertedUsers.get(0).getEmail()).isEqualTo(userResponseDto1.getEmail());
        assertThat(convertedUsers.get(1).getEmail()).isEqualTo(userResponseDto2.getEmail());
    }

    @DisplayName("login_정상 유저일 경우")
    @Test
    void test_login_normalUser() throws Exception {
        UserLoginDto normalUserLogin = new UserLoginDto(existentUser.getEmail(), existentUser.getPassword());

        doReturn("Afsd")
                .when(authenticationManagerBuilder)
                .getObject()
                .authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class));

        MvcResult mvcResult = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(normalUserLogin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @DisplayName("login_미등록 유저일 경우")
    @Test
    void test_login_unRegisteredUser() throws Exception {
        UserLoginDto nonExistsUserLogin = new UserLoginDto(normalUserSignup.getEmail(), normalUserSignup.getPassword());

        doThrow(BadCredentialsException.class)
                .when(authenticationManagerBuilder)
                .getObject()
                .authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class));

        MvcResult mvcResult = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nonExistsUserLogin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}