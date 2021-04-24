package com.skhu.luxuryshop.product.dto;

import com.skhu.luxuryshop.product.entity.ProductEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductResponseDto {
    private Long id;
    private String productName;
    private String productContent;
    private Integer productPrice;
    private String productCategory;
    private List<String> productImageurl;

    public ProductResponseDto(Long id, String productName, String productContent, Integer productPrice, String productCategory,List<String> productImageurl) {
        this.id = id;
        this.productName = productName;
        this.productContent = productContent;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productImageurl = productImageurl;
    }

    public static ProductResponseDto from(ProductEntity productEntity) {
        return new ProductResponseDto(productEntity.getId(), productEntity.getProductName(), productEntity.getProductContent(),
                productEntity.getProductPrice(), productEntity.getProductCategory(), productEntity.getProductImageurl());
    }
}
