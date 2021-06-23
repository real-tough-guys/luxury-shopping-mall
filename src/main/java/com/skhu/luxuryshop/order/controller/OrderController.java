package com.skhu.luxuryshop.order.controller;

import com.skhu.luxuryshop.order.dto.OrderRequestDto;
import com.skhu.luxuryshop.order.dto.OrderResponseDto;
import com.skhu.luxuryshop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> addOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        Long createdID = orderService.save(orderRequestDto);
        return ResponseEntity.created(URI.create("/")).body(createdID);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrders(@RequestParam Long userId) {
        return ResponseEntity.ok(orderService.findAll(userId));
    }
}
