package com.skhu.luxuryshop.product.dto;

import com.skhu.luxuryshop.product.entity.ProductEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductRequestDto {


    private String name;
    private String content;
    private String price;
    private String category;
    private String imageurl1;


    @Builder
    public ProductRequestDto(String name,String content, String price, String category, String imageurl1) {

        this.name = name;
        this.content=content;
        this.price = price;
        this.category = category;
        this.imageurl1 = imageurl1;

    }

    public ProductEntity toProductEntity() {
        ProductEntity productEntity = ProductEntity.builder()
                .name(name)
                .content(content)
                .price(price)
                .category(category)
                .imageurl1(imageurl1)
                .build();
        return productEntity;
    }



}
