package com.example.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entities.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    
}
