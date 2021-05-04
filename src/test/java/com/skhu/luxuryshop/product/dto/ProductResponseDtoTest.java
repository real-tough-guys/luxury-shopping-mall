package com.skhu.luxuryshop.product.dto;

import com.skhu.luxuryshop.product.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseDtoTest {
    private ProductEntity product;
    private ProductResponseDto productResponseDto;

    @BeforeEach
    void setUp() {
        List<String> imageUrl = new ArrayList<>();
        imageUrl.add("test1.jpg");
        imageUrl.add("test2.jpg");
        product = new ProductEntity(1L, "DTO 테스트", "DTO 테스트", 900, "DTO 테스트", imageUrl);
    }

    @DisplayName("form_productentity")
    @Test
    void test_from() {
        productResponseDto = ProductResponseDto.from(product);
        assertThat(product.getId()).isEqualTo(productResponseDto.getId());
        assertThat(product.getProductContent()).isEqualTo(productResponseDto.getProductContent());
        assertThat(product.getProductName()).isEqualTo(productResponseDto.getProductName());
    }
}