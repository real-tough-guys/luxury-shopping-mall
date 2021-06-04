package com.skhu.luxuryshop.cart.service;

import com.skhu.luxuryshop.cart.dto.CartRequestDto;
import com.skhu.luxuryshop.cart.dto.CartResponseDto;
import com.skhu.luxuryshop.cart.entity.Cart;
import com.skhu.luxuryshop.cart.exception.NotExistCartException;
import com.skhu.luxuryshop.cart.repository.CartRepository;
import com.skhu.luxuryshop.product.entity.ProductEntity;
import com.skhu.luxuryshop.product.exception.ProductFindByIdException;
import com.skhu.luxuryshop.product.repository.ProductRepository;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.NoUserFoundException;
import com.skhu.luxuryshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<CartResponseDto> findAll() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().map(CartResponseDto::from).collect(Collectors.toList());
    }

    public CartResponseDto findById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new NotExistCartException("장바구니  : " + id + "이 존재 하지 않습니다."));
        return CartResponseDto.from(cart);
    }

    public List<CartResponseDto> findByUserId(Long id) {
        List<Cart> carts = cartRepository.findByUserId(id);
        return carts.stream()
                .map(CartResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartResponseDto addCart(CartRequestDto cartRequestDto) {
        ProductEntity productOpt = productRepository.findById(cartRequestDto.getProductId())
                .orElseThrow(() -> new ProductFindByIdException("상품 id : " + cartRequestDto.getProductId() + "이 존재 하지 않습니다."));
        UserEntity user = userRepository.findById(cartRequestDto.getUserId())
                .orElseThrow(NoUserFoundException::new);

        return CartResponseDto.from(cartRepository.save(Cart.builder()
                .product(productOpt)
                .user(user)
                .color(cartRequestDto.getColor())
                .size(cartRequestDto.getSize())
                .build()));
    }

    @Transactional
    public void delete(Long id) {
        if (!cartRepository.findById(id).isPresent()) {
            throw new NotExistCartException("존재하지 않는 장바구니 입니다.");
        }
        cartRepository.deleteById(id);
    }
}