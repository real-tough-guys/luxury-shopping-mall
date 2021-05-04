package com.skhu.luxuryshop.product.service;

import com.skhu.luxuryshop.product.exception.ProductExistByIdException;
import com.skhu.luxuryshop.product.exception.ProductFindByIdException;
import com.skhu.luxuryshop.product.dto.ProductRequestDto;
import com.skhu.luxuryshop.product.dto.ProductResponseDto;
import com.skhu.luxuryshop.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    private ProductRequestDto product;
    private ProductRequestDto existProduct;

    @BeforeEach
    void setUp() {
        List<String> imageUrl = new ArrayList<>();
        imageUrl.add("test1.jpg");
        imageUrl.add("test2.jpg");
        existProduct = new ProductRequestDto("서비스Test", "서비스 컨텐트 테스트", 900, "아우터", imageUrl);
        productRepository.save(existProduct.toProductEntity());
        product = new ProductRequestDto("서비스Test", "서비스 컨텐트 테스트", 900, "아우터", imageUrl);
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
    }


    @Test
    void 상품_Id_조회_Test() throws ProductFindByIdException {
        ProductResponseDto testProduct = productService.findById(1L);
        assertThat(existProduct.getProductName()).isEqualTo(testProduct.getProductName());
    }

    @DisplayName("상품 ID가 존재하지 않는 경우 throw ProductFindByIdException")
    @Test
    void 상품_Id_Test() {
        assertThatThrownBy(() -> {
            productService.findById(2L);
        }).isInstanceOf(ProductFindByIdException.class)
                .hasMessageContaining("상픔 ID가 존재하지 않습니다.");
    }

    @Test
    void 상품_전체_조회_Test() {
        List<ProductResponseDto> productList = productService.findAll();
        then(!productList.isEmpty());
        for (ProductResponseDto product : productList) {
            assertThat(existProduct.getProductName()).isEqualTo(product.getProductName());
            assertThat(existProduct.getProductContent()).isEqualTo(product.getProductContent());
        }
    }

    @DisplayName("상품 저장")
    @Test
    void 상품_저장_Test() {
        ProductResponseDto testProduct = productService.save(product);
        assertThat(product.getProductName()).isEqualTo(testProduct.getProductName());
    }

    @Test
    void 상품_수정_Test() {
        ProductResponseDto responseDto = productService.save(product);
        List<String> imageUrlTest = new ArrayList<>();
        imageUrlTest.add("test3.jpg");
        imageUrlTest.add("test4.jpg");
        ProductRequestDto testProduct = new ProductRequestDto("서비스Test", "서비스 컨텐트 테스트", 800, "아우터", imageUrlTest);
        productService.update(responseDto.getId(), testProduct);
        assertThat(responseDto.getProductContent()).isEqualTo(testProduct.getProductContent());
        assertThat(responseDto.getProductPrice()).isNotEqualTo(testProduct.getProductPrice());
    }

    @Test
    void 상품_삭제_Test() {
        productService.delete(1L);
        assertThat(productRepository.findById(1L)).isEmpty();
    }

    @DisplayName("삭제하려는 상품 ID가 존재하지 않는 경우 throw ProductFindByExistIdException")
    @Test
    void 삭제_Id_Test_() {
        assertThatThrownBy(() -> {
            productService.delete(2L);
        }).isInstanceOf(ProductExistByIdException.class)
                .hasMessageContaining("삭제하려는 ID 없음");
    }
}

