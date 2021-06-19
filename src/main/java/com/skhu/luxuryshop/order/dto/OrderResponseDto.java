package com.skhu.luxuryshop.order.dto;

import com.skhu.luxuryshop.cart.entity.Cart;
import com.skhu.luxuryshop.order.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private List<Cart> carts;
    private String totalMoney;
    private String myAddress;
    private String deliveryMessage;

    public static OrderResponseDto from(OrderEntity order) {
        return new OrderResponseDto(order.getId(), order.getCarts(),order.getTotalMoney(), order.getMyAddress(), order.getDeliveryMessage());
    }
}