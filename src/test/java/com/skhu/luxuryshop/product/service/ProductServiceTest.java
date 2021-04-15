package com.skhu.luxuryshop.product.service;

import com.skhu.luxuryshop.exception.ProductExistByIdException;
import com.skhu.luxuryshop.exception.ProductFindByIdException;
import com.skhu.luxuryshop.product.dto.ProductRequestDto;
import com.skhu.luxuryshop.product.dto.ProductResponseDto;
import com.skhu.luxuryshop.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        existProduct = new ProductRequestDto("서비스Test", "서비스 컨텐트 테스트", 900, "아우터", "www.asd");
        productRepository.save(existProduct.toProductEntity());
        product = new ProductRequestDto("서비스Test", "서비스 컨텐트 테스트", 900, "아우터", "www.asd");
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
    }


    @Test
    void 상품_Id_조회_Test() throws ProductFindByIdException {
        ProductResponseDto testProduct = productService.findById(1L);
        assertThat(existProduct.getName()).isEqualTo(testProduct.getName());
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
            assertThat(existProduct.getName()).isEqualTo(product.getName());
            assertThat(existProduct.getContent()).isEqualTo(product.getContent());
        }
    }

    @DisplayName("상품 저장")
    @Test
    void 상품_저장_Test() {
        ProductResponseDto testProduct = productService.save(product);
        assertThat(product.getName()).isEqualTo(testProduct.getName());
    }

    @Test
    void 상품_수정_Test() {
        ProductResponseDto responseDto = productService.save(product);
        ProductRequestDto testProduct = new ProductRequestDto("서비스Test", "서비스 컨텐트 테스트", 800, "아우터", "www.asd");
        productService.update(responseDto.getId(), testProduct);
        assertThat(responseDto.getContent()).isEqualTo(testProduct.getContent());
        assertThat(responseDto.getPrice()).isNotEqualTo(testProduct.getPrice());
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

