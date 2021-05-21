package com.skhu.luxuryshop.product.dto;

import com.skhu.luxuryshop.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String productName;
    private String productContent;
    private Integer productPrice;
    private String productCategory;
    private List<String> productImageurl;
    private List<String> productSize;
    private List<String> productColor;

    public static ProductResponseDto from(ProductEntity productEntity) {
        return new ProductResponseDto(productEntity.getId(), productEntity.getProductName(), productEntity.getProductContent(),
                productEntity.getProductPrice(), productEntity.getProductCategory(), productEntity.getProductImageurl(),productEntity.getProductSize(),productEntity.getProductColor());
    }
}
