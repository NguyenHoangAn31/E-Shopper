package com.example.security.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entities.ProductVariant;

public interface ProductVariantRepositry extends JpaRepository<ProductVariant, Integer>{
    List<ProductVariant> findByProductId(int productId);

}
