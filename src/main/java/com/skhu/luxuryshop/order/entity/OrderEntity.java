package com.skhu.luxuryshop.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.skhu.luxuryshop.cart.entity.Cart;
import com.skhu.luxuryshop.product.entity.ProductEntity;
import com.skhu.luxuryshop.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    @OneToMany
    @JoinColumn(name = "cart_id")
    @JsonManagedReference
    private List<Cart> carts;

    @NotNull
    private String totalMoney;

    @NotNull
    private String myAddress;

    @NotNull
    private String deliveryMessage;

    @Builder
    public OrderEntity(UserEntity user, List<Cart> carts,String totalMoney, String myAddress, String deliveryMessage) {
        this.user = user;
        this.carts = carts;
        this.totalMoney=totalMoney;
        this.myAddress = myAddress;
        this.deliveryMessage = deliveryMessage;
    }

}
