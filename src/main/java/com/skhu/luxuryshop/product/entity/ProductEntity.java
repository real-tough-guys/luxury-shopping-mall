package com.skhu.luxuryshop.product.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(length = 50)
    private String price;
    @Column(length = 50)
    private String category;
    @Column(length = 200)
    private String imageurl1;


    @Builder
    public ProductEntity(String name,String content, String price, String category, String imageurl1) {

        this.name = name;
        this.content = content;
        this.price = price;
        this.category = category;
        this.imageurl1 = imageurl1;

    }


}