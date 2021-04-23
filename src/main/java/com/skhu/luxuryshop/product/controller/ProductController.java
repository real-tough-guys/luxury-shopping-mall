package com.skhu.luxuryshop.product.controller;

import com.skhu.luxuryshop.product.dto.ProductRequestDto;
import com.skhu.luxuryshop.product.dto.ProductResponseDto;
import com.skhu.luxuryshop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Long> registerProduct(ProductRequestDto productDto ,List<MultipartFile> files) {
        String baseDir = "C:\\SKHU-project\\SW-Capstone-Project\\luxury-shopping-mall\\src\\frontend\\src\\assets\\images\\";
        if (files != null) {
            try {
                for (int i = 0; i < files.size(); i++) {
                    files.get(i).transferTo(new File(baseDir + files.get(i).getOriginalFilename()));
                }
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        ProductResponseDto savedProduct = productService.save(productDto);
        return ResponseEntity.created(URI.create("/" + savedProduct.getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto productRequestDto) {
        productService.update(id, productRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}