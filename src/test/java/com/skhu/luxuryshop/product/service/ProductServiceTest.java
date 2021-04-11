package com.skhu.luxuryshop.product.service;

import com.skhu.luxuryshop.product.dto.ProductRequestDto;
import com.skhu.luxuryshop.product.dto.ProductResponseDto;
import com.skhu.luxuryshop.product.repository.ProductRepository;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    private ProductRequestDto product;

    @BeforeEach
    void setup() {
        product = new ProductRequestDto( "서비스Test", "서비스 컨텐트 테스트", 900, "아우터", "www.asd" );
    }

    @After
    public void cleanup() {
        productRepository.deleteAll();
    }

    @Test
    void 상품_저장_Test() {
        Long id = productService.save( product );
        ProductResponseDto productResponseDto = productService.findById( id );
        assertTrue( productResponseDto.getName().equals( product.getName() ) );
        assertTrue( productResponseDto.getContent().equals( product.getContent() ) );
    }
}