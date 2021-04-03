package com.skhu.luxuryshop.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.exception.SignupPasswordUnmatchedException;
import com.skhu.luxuryshop.user.service.UserSignupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureWebMvc
public class UserControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserSignupService userSignupService;

    UserEntity user;
    UserSignupDto normalUserSignup;
    UserSignupDto emailDuplicatedUserSignup;
    UserSignupDto unmatchedPwdUserSignup;

    @BeforeEach
    void prepare() {
        user = new UserEntity(999, "test123@gmail.co.kr", "password", "홍길홍");
        normalUserSignup = new UserSignupDto("unDuplicatied@gmail.co.kr", "password", "password", "홍길동");
        emailDuplicatedUserSignup = new UserSignupDto("test123@gmail.co.kr", "password", "password", "홍길동");
        unmatchedPwdUserSignup = new UserSignupDto("unDuplcatied1@gmail.co.kr", "password", "wrongPwd", "홍길동");
    }

    @Test
    void test_isDuplicatedEmail() throws Exception {
        when(userSignupService.isDuplicatedEmail(ArgumentMatchers.anyString()))
                .thenReturn(false);
        when(userSignupService.isDuplicatedEmail(user.getEmail()))
                .thenThrow(DuplicatedEmailException.class);

        mockMvc.perform(get("/api/users/email").content(normalUserSignup.getEmail()))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get("/api/users/email").content(emailDuplicatedUserSignup.getEmail()))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    /*
    * doThrow로 userSignupService의  save메소드가 호출되었을 때 exception을 날려야하는데 안날려짐
    */
    void test_signup() throws Exception {
        when(userSignupService.isDuplicatedEmail(user.getEmail()))
                .thenThrow(DuplicatedEmailException.class);
        doThrow(DuplicatedEmailException.class)
                .when(userSignupService)
                .save(emailDuplicatedUserSignup);
        doThrow(SignupPasswordUnmatchedException.class)
                .when(userSignupService)
                .save(unmatchedPwdUserSignup);

        mockMvc.perform(post("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(normalUserSignup)))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(post("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailDuplicatedUserSignup)))
                .andExpect(status().isBadRequest())
                .andDo(print());
        mockMvc.perform(post("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unmatchedPwdUserSignup)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}