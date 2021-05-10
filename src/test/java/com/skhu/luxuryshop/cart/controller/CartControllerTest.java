package com.skhu.luxuryshop.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhu.luxuryshop.cart.dto.CartRequestDto;
import com.skhu.luxuryshop.cart.dto.CartResponseDto;
import com.skhu.luxuryshop.cart.entity.Cart;
import com.skhu.luxuryshop.cart.service.CartService;
import com.skhu.luxuryshop.product.entity.ProductEntity;
import com.skhu.luxuryshop.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CartController.class)
@AutoConfigureWebMvc
class CartControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private CartService cartService;

    private ProductEntity product;
    private CartRequestDto saveCartDto;


    @BeforeEach
    void setUp() {
        product = ProductEntity.builder()
                .id(1L)
                .productName("test")
                .productContent("test")
                .productPrice(123)
                .productCategory("test")
                .build();
        saveCartDto = new CartRequestDto(1L, "노란색");

    }

    @DisplayName("장바구니 조회")
    @Test
    void getCartList() throws Exception {
        Cart cart = Cart.builder()
                .id(1L)
                .product(product)
                .color("노랑").build();

        given(cartService.findById(1L)).willReturn(CartResponseDto.from(cart));

        final ResultActions actions = mockMvc.perform(get("/api/carts/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.product.productName", is("test")))
                .andDo(print());
    }

    @DisplayName("장바구니 추가")
    @Test
    void addCart() throws Exception {
        given(cartService.addCart(new CartRequestDto(1L, "노랑")))
                .willReturn(CartResponseDto.from(
                        Cart.builder()
                        .id(1L)
                        .product(product)
                        .color("노랑").build()));

        final ResultActions actions = mockMvc.perform(post("/api/carts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());


    }

}