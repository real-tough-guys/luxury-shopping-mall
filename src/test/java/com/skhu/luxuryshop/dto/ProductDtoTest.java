package com.skhu.luxuryshop.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDtoTest {
    @Test
    public void DTO테스트() {
        long id = 6;
        String productName = "무신사";
        String productPrice = "무신사";
        String productCategory = "무신사";
        String imageurl1 = "무신사";
        ProductDto dto = new ProductDto(id, productName, productPrice, productCategory, imageurl1);
        assertThat(dto.getId()).isEqualTo(id);
    }

}