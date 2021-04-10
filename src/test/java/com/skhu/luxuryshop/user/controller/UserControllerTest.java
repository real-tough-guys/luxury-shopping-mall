package com.skhu.luxuryshop.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.service.UserSignupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
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
    UserEntity normalUserEntity;

    UserSignupDto normalUserSignup;
    UserSignupDto emailDuplicatedUserSignup;

    UserResponseDto normalUserResponse;

    @BeforeEach
    void prepare() {
        user = new UserEntity(999L, "test123@gmail.co.kr", "password", "홍길홍");

        normalUserSignup = new UserSignupDto("unDuplicated1@gmail.co.kr", "password", "password", "홍길동");
        emailDuplicatedUserSignup = new UserSignupDto(user.getEmail(), "password", "password", "홍길동");

        normalUserEntity = new UserEntity(9999L, normalUserSignup.getEmail(), normalUserSignup.getPassword(), normalUserSignup.getNickname());
        normalUserResponse = UserResponseDto.from(normalUserEntity);
    }

    @Test
    void test_isDuplicatedEmail() throws Exception {
        doThrow(DuplicatedEmailException.class)
                .when(userSignupService)
                .validateDuplicatedEmail(user.getEmail());

        mockMvc.perform(get("/api/users/emails/" + normalUserSignup.getEmail() + "/exists"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get("/api/users/emails/" + emailDuplicatedUserSignup.getEmail() + "/exists"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void test_signup() throws Exception {
        when(userSignupService.save(ArgumentMatchers.any(UserSignupDto.class)))
                .thenReturn(normalUserResponse);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(normalUserSignup)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}