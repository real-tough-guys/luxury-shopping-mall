package com.skhu.luxuryshop.product.dto;

import com.skhu.luxuryshop.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProductRequestDto {
    @NotBlank(message = "상품명이 없습니다")
    private String productName;
    @NotBlank(message = "상품 설명이 없습니다")
    private String productContent;
    @PositiveOrZero(message = "숫자를 정확히 입력해주세요 ex) 500")
    private Integer productPrice;
    @NotBlank(message = "카테고리가 없습니다.")
    private String productCategory;
    @NotNull(message = "이미지가 없습니다")
    private List<String> productImageurl;
    @NotNull(message = "사이즈가 없습니다.")
    private List<String> productSize;
    @NotNull(message = "색상이 없습니다.")
    private List<String> productColor;

    public ProductEntity toProductEntity() {
        ProductEntity productEntity = ProductEntity.builder()
                .productName(productName)
                .productContent(productContent)
                .productPrice(productPrice)
                .productCategory(productCategory)
                .productImageurl(productImageurl)
                .productSize(productSize)
                .productColor(productColor)
                .build();
        return productEntity;
    }
}
