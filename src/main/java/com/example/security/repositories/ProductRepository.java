package com.example.security.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Lấy 8 sản phẩm có stock thấp nhất
    List<Product> findTop8ByOrderByStockAsc();

    // Lấy 8 sản phẩm có stock cao nhất
    List<Product> findTop8ByOrderByStockDesc();

    List<Product> findByCategory_Name(String name);
}
