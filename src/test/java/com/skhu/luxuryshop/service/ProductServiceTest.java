package com.skhu.luxuryshop.service;

import com.skhu.luxuryshop.dto.ProductDto;
import com.skhu.luxuryshop.entity.ProductEntity;
import com.skhu.luxuryshop.repository.ProductRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    ProductService productService2;
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductService productService;

    List<ProductEntity> productEntity;

    @Before("")
    public void prepare() {
        ProductEntity product1 = new ProductEntity(1, "미니멀 블루종", "90000", "outer", "https://image.msscdn.net/images/goods_img/20160829/401149/401149_1_125.jpg");
        this.productEntity.add(product1);
        ProductEntity product2 = new ProductEntity(2, "체크 셔츠", "80000", "top", "https://image.msscdn.net/images/goods_img/20160829/401149/401149_1_125.jpg");
        this.productEntity.add(product2);
        ProductEntity product3 = new ProductEntity(3, "블랙 니트", "70000", "top", "https://image.msscdn.net/images/goods_img/20160829/401149/401149_1_125.jpg");
        this.productEntity.add(product3);
        ProductEntity product4 = new ProductEntity(4, "가죽 자켓 ", "750000", "outer", "https://image.msscdn.net/images/goods_img/20160829/401149/401149_1_125.jpg");
        this.productEntity.add(product4);
    }

    @Test
    public void findById() {

    }

    @Test
    void findAll() {
        Mockito.when(productRepository.findAll()).thenReturn(this.productEntity);
        List<ProductEntity> productEntity2 = productService.findAll();
        assertEquals(this.productEntity, productEntity2);
        Mockito.verify(productRepository).findAll();
    }

    @Test
    void save() {
        ProductDto product1 = new ProductDto(6, "미니멀 블루종", "90000", "outer", "https://image.msscdn.net/images/goods_img/20160829/401149/401149_1_125.jpg");
        productService2.save(product1);
        System.out.println(product1.getId());
        ProductDto product2 = productService2.findById(product1.getId());//안되는 부분!!
        System.out.println(product2.getId());
        assertEquals(product1, product2);


    }
}


