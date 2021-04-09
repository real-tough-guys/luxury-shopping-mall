package com.skhu.luxuryshop.product.dto;

import com.skhu.luxuryshop.product.entity.ProductEntity;
import lombok.Getter;

@Getter
public class ProductResponseDto {
    private long id;
    private String name;
    private String content;
    private Integer price;
    private String category;
    private String imageurl;

    public ProductResponseDto(Long id, String name, String content, Integer price, String category, String imageurl) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.price = price;
        this.category = category;
        this.imageurl = imageurl;
    }

    public static ProductResponseDto from(ProductEntity productEntity) {
        return new ProductResponseDto(productEntity.getId(), productEntity.getName(), productEntity.getContent(),
                productEntity.getPrice(), productEntity.getCategory(), productEntity.getImageurl());
    }


}
