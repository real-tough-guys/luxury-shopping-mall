package com.skhu.luxuryshop.cart.service;

import com.skhu.luxuryshop.cart.dto.CartRequestDto;
import com.skhu.luxuryshop.cart.dto.CartResponseDto;
import com.skhu.luxuryshop.product.dto.ProductRequestDto;
import com.skhu.luxuryshop.product.entity.ProductEntity;
import com.skhu.luxuryshop.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CartServiceTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductRepository productRepository;

    private  CartRequestDto product;


    @BeforeEach
    void setUp() {
        List<String> imageUrl = new ArrayList<>();
        imageUrl.add("test1.jpg");
        imageUrl.add("test2.jpg");
        productRepository.save(new ProductEntity(1L, "test", "test", 11,"test",imageUrl));
        product = new CartRequestDto(1L, "색상");
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void findAll() {
        //given 뭔가가 주어졌을때

        //when 이걸 실행했을때

        //then 결과가 이게 나와야대
    }

    @Test
    void findById() {
    }

    @Test
    void addCart() {
        CartResponseDto cartResponseDto = cartService.addCart(product);
        assertThat(product.getProductId()).isEqualTo(cartResponseDto.getProduct().getId());
    }

    @DisplayName("상품 ID가 없을때")
    @Test
    public void notProductID() {

    }

    @Test
    void delete() {
    }
}