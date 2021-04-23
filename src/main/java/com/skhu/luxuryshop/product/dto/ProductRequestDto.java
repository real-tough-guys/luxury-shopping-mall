package com.skhu.luxuryshop.product.dto;

import com.skhu.luxuryshop.product.entity.ProductEntity;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
public class ProductRequestDto {
    @NotBlank(message = "상품명이 없습니다")
    private String name;
    @NotBlank(message = "상품 설명이 없습니다")
    private String content;
    @PositiveOrZero(message = "숫자를 정확히 입력해주세요 ex) 500")
    private Integer price;
    private String category;
    private List<String> imageurl;

    @Builder
    public ProductRequestDto(String name, String content, Integer price, String category, List<String> imageurl) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.category = category;
        this.imageurl= imageurl;
    }

    public ProductEntity toProductEntity() {
        ProductEntity productEntity = ProductEntity.builder()
                .name(name)
                .content(content)
                .price(price)
                .category(category)
                .imageurl(imageurl)
                .build();
        return productEntity;
    }
}
