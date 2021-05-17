package com.skhu.luxuryshop.product.repository;

import com.skhu.luxuryshop.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsById(Long id);
    List<ProductEntity> findByProductNameContaining(String keyword);
    List<ProductEntity> findByProductCategoryContaining(String keword);
}