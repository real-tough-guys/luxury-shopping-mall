package com.skhu.luxuryshop.cart.controller;

import com.skhu.luxuryshop.cart.dto.CartRequestDto;
import com.skhu.luxuryshop.cart.dto.CartResponseDto;
import com.skhu.luxuryshop.cart.entity.Cart;
import com.skhu.luxuryshop.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List> getCarts() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDto> getCartId(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.findById(id));
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<Cart>> getMyCarts(@PathVariable Long userId) {
        List<CartResponseDto> carts = cartService.findByUserId(userId);
        return new ResponseEntity(carts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addCart(@RequestBody @Valid CartRequestDto cartDto) {
        CartResponseDto savedCart = cartService.addCart(cartDto);
        return ResponseEntity.created(URI.create("/" + savedCart.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCart(@PathVariable Long id) {
        cartService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}