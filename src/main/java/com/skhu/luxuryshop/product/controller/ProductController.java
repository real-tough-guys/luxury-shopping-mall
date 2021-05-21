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
import java.net.URI;
import java.util.List;

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

    @GetMapping("/main")
    public ResponseEntity<List<ProductResponseDto>> getProductsMain(@RequestParam(value = "limit", defaultValue = "1") int limit) {
        return ResponseEntity.ok(productService.findAllMain(limit));
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchKeyword(@RequestParam String name ,@RequestParam String category) {
        return ResponseEntity.ok(productService.findBySearchKeyword(name,category));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }
    @PostMapping
    public ResponseEntity<Long> registerProduct(@RequestBody @Valid ProductRequestDto productDto) {
        ProductResponseDto savedProduct = productService.save(productDto);
        return ResponseEntity.created(URI.create("/")).body(savedProduct.getId());
    }
    @PostMapping("/file")
    public ResponseEntity uploadImage(@RequestBody List<MultipartFile> files){
        if (files != null) {
            productService.uploadFiles(files);
        }
        return ResponseEntity.ok("이미지 저장완료");
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<Long> updateProduct(@PathVariable Long id, ProductRequestDto productRequestDto, List<MultipartFile> files) {
        if (files != null) {
            productService.uploadFiles(files);
        }
        productService.update(id, productRequestDto);
        return ResponseEntity.ok(productService.update(id, productRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}