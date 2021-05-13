package com.skhu.luxuryshop.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
public class CartRequestDto {
    @Positive(message = "없음")
    private Long productId;
    @Positive(message = "없음")
    private Long userId;
    @NotBlank(message = "색상.")
    private String color;
}
