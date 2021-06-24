package com.skhu.luxuryshop.order.dto;

import com.skhu.luxuryshop.cart.entity.Cart;
import com.skhu.luxuryshop.order.entity.OrderEntity;
import com.skhu.luxuryshop.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderRequestDto {
    @Positive(message = "없음")
    private Long userId;
    @NotNull(message = "장바구니가 없습니다.")
    private List<Long> cartId;
    @NotBlank(message = "주문 합산 가격이 없습니다")
    private String totalMoney;
    @NotBlank(message = "내 주소가 없습니다.")
    private String myAddress;
    @NotBlank(message = "배송메시지가 없습니다.")
    private String deliveryMessage;

    public OrderEntity toOrderEntity(UserEntity userEntity , List<Cart> cartFilter) {
        OrderEntity order = OrderEntity.builder()
                .user(userEntity)
                .carts(cartFilter)
                .totalMoney(totalMoney)
                .myAddress(myAddress)
                .deliveryMessage(deliveryMessage)
                .build();
        return order;
    }
}
