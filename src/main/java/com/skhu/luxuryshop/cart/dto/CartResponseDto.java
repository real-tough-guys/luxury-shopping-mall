package com.skhu.luxuryshop.cart.dto;

import com.skhu.luxuryshop.cart.entity.Cart;
import com.skhu.luxuryshop.product.entity.ProductEntity;
import com.skhu.luxuryshop.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartResponseDto {
    private Long id;
    private ProductEntity product;
    private UserEntity user;
    private String color;
    private String size;

    public static CartResponseDto from(Cart cart) {
        return new CartResponseDto(cart.getId(), cart.getProduct(), cart.getUser(), cart.getColor(), cart.getSize());
    }
}