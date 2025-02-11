package com.example.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
}
