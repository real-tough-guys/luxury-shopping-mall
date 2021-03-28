package com.skhu.luxuryshop.service;

import com.skhu.luxuryshop.dto.ProductDto;
import com.skhu.luxuryshop.entity.ProductEntity;
import com.skhu.luxuryshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductService {


    private ProductRepository productRepository;


    @Transactional
    public ProductDto findById(Long id) {
        Optional<ProductEntity> productEntityWrapper = productRepository.findById(id);
        ProductEntity productEntity = productEntityWrapper.get();//entity 만 쏙 빼는거
        return convertEntityToDto(productEntity);
    }

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public long save(ProductDto productDto) {
        ProductEntity productEntity = productDto.toEntity();
        return productRepository.save(productEntity).getId();
    }

    private ProductDto convertEntityToDto(ProductEntity product) { //엔티티 객체 변수를 디티오 객체 변수로 변환
        return ProductDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productCategory(product.getProductCategory())
                .imageurl1(product.getImageurl1())
                .build();
    }


}
