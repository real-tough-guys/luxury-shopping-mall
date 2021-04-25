package com.skhu.luxuryshop.product.entity;

import com.skhu.luxuryshop.product.dto.ProductRequestDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column
    private String productName;
    @NotNull
    @Column
    private String productContent;
    @NotNull
    @Column
    private Integer productPrice;
    @NotNull
    @Column
    private String productCategory;
    @NotNull
    @Column
    @ElementCollection
    private List<String> productImageurl;


    @Builder
    public ProductEntity(String productName, String productContent, Integer productPrice, String productCategory, List<String> productImageurl) {
        this.productName = productName;
        this.productContent = productContent;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productImageurl = productImageurl;
    }

    public void update(String productName, String productContent, Integer productPrice, String productCategory, String productImageurl) {
        this.productName = productName;
        this.productContent = productContent;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productImageurl = productImageurl;
    }


}