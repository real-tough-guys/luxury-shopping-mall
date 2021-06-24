package com.skhu.luxuryshop.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {
    @Positive(message = "없음")
    private Long productId;
    @Positive(message = "없음")
    private Long userId;
    @NotBlank(message = "색상.")
    private String color;
    @NotBlank(message = "사이즈.")
    private String size;
}