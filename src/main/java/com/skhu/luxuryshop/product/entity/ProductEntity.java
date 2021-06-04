package com.skhu.luxuryshop.product.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String productName;
    @NotNull
    private String productContent;
    @NotNull
    private Integer productPrice;
    @NotNull
    private String productCategory;
    @NotNull
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_imageurl", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> productImageurl;
    @NotNull
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_size", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> productSize;
    @ElementCollection(fetch = FetchType.LAZY)
    @NotNull
    @CollectionTable(name = "product_color", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> productColor;

    public void update(ProductEntity productEntity) {
        this.productName = productEntity.getProductName();
        this.productContent = productEntity.getProductContent();
        this.productPrice = productEntity.getProductPrice();
        this.productCategory = productEntity.getProductCategory();
        this.productImageurl = productEntity.getProductImageurl();
        this.productSize = productEntity.getProductSize();
        this.productColor = productEntity.getProductColor();
    }
}