package com.skhu.luxuryshop.product.service;

import com.skhu.luxuryshop.exception.ProductFindByIdException;
import com.skhu.luxuryshop.product.dto.ProductRequestDto;
import com.skhu.luxuryshop.product.dto.ProductResponseDto;
import com.skhu.luxuryshop.product.entity.ProductEntity;
import com.skhu.luxuryshop.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {//Constructor Injection
        this.productRepository = productRepository;
    }

    public ProductResponseDto findById(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ProductFindByIdException("상품 id : " + id + "이 존재 하지 않습니다."));
        return ProductResponseDto.from(productEntity);
    }

    @Transactional
    public Long update(Long id, ProductRequestDto productRequestDto) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new ProductFindByIdException("업데이트 하려는 상품 id : " + id + "이 존재 하지 않습니다."));
        productEntity.update(productRequestDto);
        return productEntity.getId();
    }

    public List<ProductResponseDto> findAll() {
        List<ProductEntity> productEntitys = productRepository.findAll();
        return productEntitys.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long save(ProductRequestDto productDto) {
        return productRepository.save(productDto.toProductEntity()).getId();
    }

    @Transactional
    public void delete(Long id) {
        productRepository.findById(id).orElseThrow(() -> new ProductFindByIdException("삭제 하려는 상품 id : " + id + "이 존재 하지 않습니다."));
        productRepository.deleteById(id);
    }


}
