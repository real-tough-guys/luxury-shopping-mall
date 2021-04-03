package com.skhu.luxuryshop.product.repository;

import com.skhu.luxuryshop.product.entity.ProductEntity;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductReposioryTest {
    @Autowired
    ProductRepository productRepository;

    @After
    public void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    public void 상품_저장하고_불러오기() {
        String name = "test 레포지토리";
        String content = "test 상품 콘텐트";
        String price = "무신사";
        String category = "무신사";
        String imageurl1 = "무신사";
        productRepository.save(ProductEntity.builder()
                                            .name(name)
                                            .content(content)
                                            .price(price)
                                            .category(category)
                                            .imageurl1(imageurl1)
                                            .build());

        List<ProductEntity> productList = productRepository.findAll();
        ProductEntity products = productList.get(0);
        assertThat(products.getName()).isEqualTo(name);
        assertThat(products.getContent()).isEqualTo(content);
    }
}
