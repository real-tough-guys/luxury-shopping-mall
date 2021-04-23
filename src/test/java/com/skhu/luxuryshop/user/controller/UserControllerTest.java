package com.skhu.luxuryshop.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.exception.SignupPasswordUnmatchedException;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureWebMvc
public class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserSignupService userSignupService;

    private UserSignupDto normalUserSignup;
    private UserSignupDto duplicatedEmailUserSignup;
    private UserSignupDto unmatchedPwdUserSignup;

    @BeforeEach
    void setUp() {
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
        doThrow(new DuplicatedEmailException("중복된 이메일입니다."))
                .when(userSignupService)
                .validateDuplicatedEmail(duplicatedEmailUserSignup.getEmail());

        MvcResult mvcResult =  mockMvc.perform(get("/api/users/emails/" + duplicatedEmailUserSignup.getEmail() + "/exists"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("중복된 이메일입니다.");
    }

    @DisplayName("save_정상 유저인 경우 isCreated")
    @Test
    void test_signUp_normalUser() throws Exception {
        UserResponseDto normalUserResponse = new UserResponseDto(1L, normalUserSignup.getEmail(), normalUserSignup.getNickname());
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

    @DisplayName("save_중복된 이메일을 가진 유저인 경우 BadRequest")
    @Test
    void test_signUp_duplicatedEmailUser() throws Exception {
        when(userSignupService.save(ArgumentMatchers.any(UserSignupDto.class)))
                .thenThrow(new DuplicatedEmailException("중복된 이메일입니다."));

        MvcResult mvcResult = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicatedEmailUserSignup)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("중복된 이메일입니다.");
    }

    @DisplayName("save_비밀번호 불일치 유저인 경우 BadRequest")
    @Test
    void test_signUp_unmatchedPasswordUser() throws Exception {
        unmatchedPwdUserSignup = new UserSignupDto("test123@gmail.com", "password", "wrongpwd", "홍길동");

        when(userSignupService.save(ArgumentMatchers.any(UserSignupDto.class)))
                .thenThrow(new SignupPasswordUnmatchedException("비밀번호가 일치하지 않습니다."));

        MvcResult mvcResult = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unmatchedPwdUserSignup)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo("비밀번호가 일치하지 않습니다.");
    }
}