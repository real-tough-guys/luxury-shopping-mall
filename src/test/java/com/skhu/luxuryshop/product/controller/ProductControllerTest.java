package com.skhu.luxuryshop.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhu.luxuryshop.product.dto.ProductRequestDto;
import com.skhu.luxuryshop.product.dto.ProductResponseDto;
import com.skhu.luxuryshop.product.entity.ProductEntity;
import com.skhu.luxuryshop.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    private ProductRequestDto product;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        product = new ProductRequestDto("controller 테스트", "controller 테스트", 900, "controller 테스트", "controller 테스트");
        productEntity = new ProductEntity(1L, "controller test", "controller", 900, "controller", "controller");
    }

    @Test
    void 상품등록_Test() throws Exception {
        ProductResponseDto productResponse = new ProductResponseDto(1L, product.getName(), product.getContent(), product.getPrice()
                , product.getCategory(), product.getImageurl());
        when(productService.save(ArgumentMatchers.any(ProductRequestDto.class)))
                .thenReturn(productResponse);
        MvcResult mvcResult = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
        String location = mvcResult.getResponse().getHeader("Location");
        assertThat(location).isEqualTo("/" + productResponse.getId());
    }

    @Test
    void 상품ID조회_Test() throws Exception {
        given(productService.findById(1L)).willReturn(ProductResponseDto.from(productEntity));
        mockMvc.perform(get("/api/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 상품업데이트_Test() throws Exception {
        given(productService.findById(1L)).willReturn(ProductResponseDto.from(productEntity));
        ProductRequestDto productRequestDto = new ProductRequestDto("update", "update", 900, "update", "update");
        String json = objectMapper.writeValueAsString(productRequestDto);
        MvcResult mvcResult = mockMvc.perform(put("/api/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isNotEqualTo(productEntity);
    }

    @Test
    void 상품삭제_Test() throws Exception {
        given(productService.findById(1L)).willReturn(ProductResponseDto.from(productEntity));
        mockMvc.perform(delete("/api/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andDo(print());
    }
}