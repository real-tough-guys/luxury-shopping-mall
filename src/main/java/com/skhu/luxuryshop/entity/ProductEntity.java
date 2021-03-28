package com.skhu.luxuryshop.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productName;
    private String productPrice;
    private String productCategory;
    private String imageurl1;


    @Builder
    public ProductEntity(long id, String productName, String productPrice, String productCategory, String imageurl1) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.imageurl1 = imageurl1;

    }


}