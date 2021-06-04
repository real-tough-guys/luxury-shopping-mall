package com.skhu.luxuryshop.cart.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.skhu.luxuryshop.product.entity.ProductEntity;
import com.skhu.luxuryshop.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = ProductEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    private String color;
    private String size;

    @Builder
    public Cart(ProductEntity product, UserEntity user, String color,String size) {
        this.product = product;
        this.user = user;
        this.color = color;
        this.size = size;
    }
}