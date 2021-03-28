package com.skhu.luxuryshop.controller;

import com.skhu.luxuryshop.dto.ProductDto;
import com.skhu.luxuryshop.entity.ProductEntity;
import com.skhu.luxuryshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("")
    public List<ProductEntity> getProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductDto getUserByUserId(@PathVariable Long id) {

        return productService.findById(id);
    }

    @PostMapping("")//상품 생성
    public long registerUser(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }


}