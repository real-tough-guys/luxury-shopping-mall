package com.skhu.luxuryshop.product.controller;

import com.skhu.luxuryshop.product.dto.ProductRequestDto;
import com.skhu.luxuryshop.product.dto.ProductResponseDto;
import com.skhu.luxuryshop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Long> registerProduct(ProductRequestDto productDto, List<MultipartFile> files) {
        productService.fileUpload(files);
        ProductResponseDto savedProduct = productService.save(productDto);
        return ResponseEntity.created(URI.create("/" + savedProduct.getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto productRequestDto) {
        productService.update(id, productRequestDto);
        return ResponseEntity.ok(productService.update(id, productRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}