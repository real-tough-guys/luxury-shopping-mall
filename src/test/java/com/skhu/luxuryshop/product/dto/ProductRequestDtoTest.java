package com.skhu.luxuryshop.product.dto;

import com.skhu.luxuryshop.product.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRequestDtoTest {
    private ProductEntity product;
    private ProductRequestDto validProduct;

    @BeforeEach
    void setUp() {
        List<String> imageUrl = new ArrayList<>();
        imageUrl.add("test1.jpg");
        imageUrl.add("test2.jpg");
        validProduct = new ProductRequestDto("requestDTO 테스트", "DTO 테스트", 900, "DTO 테스트", imageUrl);
    }

    @DisplayName("유효한 Product 요청")
    @Test
    void test_toProductEntity_validProduct() {
        product = validProduct.toProductEntity();
        assertThat(validProduct.getProductName()).isEqualTo(product.getProductName());
        assertThat(validProduct.getProductContent()).isEqualTo(product.getProductContent());
        assertThat(validProduct.getProductPrice()).isEqualTo(product.getProductPrice());
    }
}