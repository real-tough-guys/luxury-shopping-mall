package com.skhu.luxuryshop.cart.entity;

import com.skhu.luxuryshop.product.entity.ProductEntity;
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
    private String color;

    @Builder
    public Cart(ProductEntity product, String color) {
        this.product = product;
        this.color = color;
    }
}
