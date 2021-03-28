package com.skhu.luxuryshop.dto;

import com.skhu.luxuryshop.entity.ProductEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductDto {
    private long id;

    private String productName;
    private String productPrice;
    private String productCategory;
    private String imageurl1;


    public ProductEntity toEntity(){
        ProductEntity boardEntity = ProductEntity.builder()
                .id(id)
                .productName(productName)
                .productPrice(productPrice)
                .productCategory(productCategory)
                .imageurl1(imageurl1)
                .build();
        return boardEntity;
    }




    @Builder
    public ProductDto(long id, String productName, String productPrice, String productCategory, String imageurl1) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.imageurl1 = imageurl1;

    }


}
