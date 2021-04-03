package com.skhu.luxuryshop.product.dto;

import com.skhu.luxuryshop.product.entity.ProductEntity;
import lombok.Getter;

@Getter
public class ProductResponseDto {
    private long id;
    private String name;
    private String content;
    private String price;
    private String category;
    private String imageurl1;


    public  ProductResponseDto (ProductEntity productEntity) { //엔티티 객체 변수를 디티오 객체 변수로 변환 //static factory method
        this.id = productEntity.getId();
        this.name = productEntity.getName();
        this.content = productEntity.getContent();
        this.price = productEntity.getPrice();
        this.category = productEntity.getCategory();
        this.imageurl1 = productEntity.getImageurl1();
    }


}
