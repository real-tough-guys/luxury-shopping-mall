package com.skhu.luxuryshop.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RouterController.class)
@AutoConfigureMockMvc
class RouterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("'/'로 get요청")
    @Test
    void indexPage() throws Exception {
        mockMvc.perform(get("/")
                .accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("'/error'로 get요청")
    @Test
    void error() throws Exception {
        mockMvc.perform(get("/error")
                .accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }
}