package com.skhu.luxuryshop.cart.dto;

import com.skhu.luxuryshop.cart.entity.Cart;
import com.skhu.luxuryshop.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartResponseDto {
    private Long id;
    private ProductEntity product;
    private String color;

    public static CartResponseDto from(Cart cart) {
        return new CartResponseDto(cart.getId(), cart.getProduct(), cart.getColor());
    }
}