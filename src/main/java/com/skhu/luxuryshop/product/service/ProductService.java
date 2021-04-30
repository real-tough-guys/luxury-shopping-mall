package com.skhu.luxuryshop.product.service;

import com.skhu.luxuryshop.exception.ProductExistByIdException;
import com.skhu.luxuryshop.exception.ProductFindByIdException;
import com.skhu.luxuryshop.product.dto.ProductRequestDto;
import com.skhu.luxuryshop.product.dto.ProductResponseDto;
import com.skhu.luxuryshop.product.entity.ProductEntity;
import com.skhu.luxuryshop.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
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
                .orElseThrow(() -> new ProductFindByIdException("상품 id : " + id + "이 존재 하지 않습니다."));
        return ProductResponseDto.from(productEntity);
    }

    @Transactional
    public Long update(Long id, ProductRequestDto requestDto) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ProductFindByIdException("상품 id : " + id + "이 존재 하지 않습니다."));
        productEntity.update(requestDto.toProductEntity());
        return id;
    }

    public List<ProductResponseDto> findAll() {
        List<ProductEntity> productEntitys = productRepository.findAll();
        return productEntitys.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDto save(ProductRequestDto productDto) {
        ProductEntity product = productDto.toProductEntity();
        return ProductResponseDto.from(productRepository.save(product));
    }

    public void fileUpload(List<MultipartFile> files) {
        String baseDir = System.getProperty("user.dir") + "\\src\\frontend\\src\\assets\\images\\";
        if (files != null) {
            try {
                for (MultipartFile file : files) {
                    log.info(String.valueOf(UUID.randomUUID()));
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
