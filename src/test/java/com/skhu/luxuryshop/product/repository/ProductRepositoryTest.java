package com.skhu.luxuryshop.product.repository;

import com.skhu.luxuryshop.product.dto.ProductRequestDto;
import com.skhu.luxuryshop.product.entity.ProductEntity;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;
    private ProductRequestDto product;

    @BeforeEach
    void setup() {
        product = new ProductRequestDto("서비스Test", "서비스 컨텐트 테스트", 900, "아우터", "www.asd");
    }

    @After
    public void cleanup() {
        productRepository.deleteAll();
        System.out.println("테스트 완료");
    }

    @Test
    public void 상품저장_불러오기() {
        productRepository.save(product.toProductEntity());
        List<ProductEntity> products = productRepository.findAll();
        ProductEntity productEntity = products.get(0);
        assertTrue(productEntity.getName().equals(product.getName()));
        assertTrue(productEntity.getContent().equals(product.getContent()));
    }
}