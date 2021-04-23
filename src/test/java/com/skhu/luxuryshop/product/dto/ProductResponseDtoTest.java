package com.skhu.luxuryshop.product.dto;

import com.skhu.luxuryshop.product.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseDtoTest {
    private ProductEntity product;
    private ProductResponseDto productResponseDto;

    @BeforeEach
    void setUp() {
        product = new ProductEntity(1L, "DTO 테스트", "DTO 테스트", 900, "DTO 테스트", "DTO 테스트");
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