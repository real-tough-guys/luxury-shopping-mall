package com.skhu.luxuryshop.order.service;

import com.skhu.luxuryshop.cart.entity.Cart;
import com.skhu.luxuryshop.cart.repository.CartRepository;
import com.skhu.luxuryshop.order.dto.OrderRequestDto;
import com.skhu.luxuryshop.order.dto.OrderResponseDto;
import com.skhu.luxuryshop.order.entity.OrderEntity;
import com.skhu.luxuryshop.order.repository.OrderRepository;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.NoUserFoundException;
import com.skhu.luxuryshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public Long save(OrderRequestDto orderRequestDto) {
        UserEntity user = userRepository.findById(orderRequestDto.getUserId())
                .orElseThrow(NoUserFoundException::new);
        List<Cart> carts = cartRepository.findAll();
        List<Cart> cartFilter = carts.stream()
                .filter(cart -> orderRequestDto.getCartId().contains(cart.getId()))
                .collect(Collectors.toList());
        OrderEntity order = orderRepository.save(orderRequestDto.toOrderEntity(user, cartFilter));
        return order.getId();
    }

    public List<OrderResponseDto> findAll(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(NoUserFoundException::new);
        List<OrderEntity> orderStatements = orderRepository.findByUserId(user.getId());
        return orderStatements.stream()
                .map(OrderResponseDto::from)
                .collect(Collectors.toList());
    }
}
