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
    private String name;
    @NotNull
    @Column
    private String content;
    @NotNull
    @Column
    private Integer price;
    @Column
    private String category;
    @ElementCollection
    private List<String> imageurl;


    @Builder
    public ProductEntity(String name, String content, Integer price, String category, List<String> imageurl) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.category = category;
        this.imageurl = imageurl;
    }

    public void update(ProductRequestDto request) {
        this.name = request.getName();
        this.content = request.getContent();
        this.price = request.getPrice();
        this.category = request.getCategory();
        this.imageurl = request.getImageurl();
    }


}