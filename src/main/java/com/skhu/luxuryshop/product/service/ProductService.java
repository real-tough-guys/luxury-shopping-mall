package com.skhu.luxuryshop.product.service;

import com.skhu.luxuryshop.product.exception.ProductExistByIdException;
import com.skhu.luxuryshop.product.exception.ProductFindByIdException;
import com.skhu.luxuryshop.product.dto.ProductRequestDto;
import com.skhu.luxuryshop.product.dto.ProductResponseDto;
import com.skhu.luxuryshop.product.entity.ProductEntity;
import com.skhu.luxuryshop.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {//Constructor Injection
        this.productRepository = productRepository;
    }

    public ProductResponseDto findById(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ProductFindByIdException("상품이 존재하지 않습니다."));
        return ProductResponseDto.from(productEntity);
    }

    @Transactional
    public Long update(Long id, ProductRequestDto requestDto) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ProductFindByIdException("상품이 존재하지 않습니다."));
        productEntity.update(requestDto.toProductEntity());
        return id;
    }

    public List<ProductResponseDto> findAll() {
        List<ProductEntity> products = productRepository.findAll();
        return products.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> findBySearchKeyword(String name, String category) {
        List<ProductEntity> products;
        if (!name.isEmpty()) {
            products = productRepository.findByProductNameContaining(name);
        } else {
            products = productRepository.findByProductCategoryContaining(category);
        }
        return products.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> findAllMain(int limit) {
        Page<ProductEntity> page = productRepository.findAll(PageRequest.of(limit - 1, 3, Sort.by(Sort.Direction.DESC, "id")));
        return page.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDto save(ProductRequestDto productDto) {
        ProductEntity product = productDto.toProductEntity();
        return ProductResponseDto.from(productRepository.save(product));
    }

    public void uploadFiles(List<MultipartFile> files) {
        String baseDir = System.getProperty("user.dir") + "\\src\\frontend\\src\\assets\\images\\";
        if (files != null) {
            try {
                for (MultipartFile file : files) {
                    file.transferTo(new File(baseDir + file.getOriginalFilename()));
                }
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductExistByIdException("삭제 하려는 상품이 존재하지 않습니다.");
        }
        productRepository.deleteById(id);
    }
}
