package com.skhu.luxuryshop.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhu.luxuryshop.product.dto.ProductRequestDto;
import com.skhu.luxuryshop.product.dto.ProductResponseDto;
import com.skhu.luxuryshop.product.entity.ProductEntity;
import com.skhu.luxuryshop.product.repository.ProductRepository;
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

import java.util.ArrayList;
import java.util.List;

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
        List<String> imageUrl = new ArrayList<>();
        imageUrl.add("test1.jpg");
        imageUrl.add("test2.jpg");
        product = new ProductRequestDto("controller 테스트", "controller 테스트", 900, "outer", imageUrl);
        productEntity = new ProductEntity(1L, "controller test", "controller", 900, "outer", imageUrl);
    }

    @Test
    void 상품등록_Test() throws Exception {
        ProductResponseDto productResponse = new ProductResponseDto(1L, product.getProductName(), product.getProductContent(), product.getProductPrice(), product.getProductCategory(), product.getProductImageurl());
        when(productService.save(ArgumentMatchers.any(ProductRequestDto.class)))
                .thenReturn(productResponse);
        MvcResult mvcResult = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
        String location = mvcResult.getResponse().getHeader("Location");
        assertThat(location + 1).isEqualTo("/" + productResponse.getId());
    }

    @Test
    void 상품ID조회_Test() throws Exception {
        given(productService.findById(1L)).willReturn(ProductResponseDto.from(productEntity));
        MvcResult mvcResult = mockMvc.perform(get("/api/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        assertThat(body).isEqualTo(productEntity);
    }

    @Test
    void 상품업데이트_Test() throws Exception {
        given(productService.findById(1L)).willReturn(ProductResponseDto.from(productEntity));
        List<String> imageUrlTest = new ArrayList<>();
        imageUrlTest.add("test3.jpg");
        imageUrlTest.add("test4.jpg");
        ProductRequestDto productRequestDto = new ProductRequestDto("update", "update", 900, "update", imageUrlTest);
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