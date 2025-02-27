package com.example.security.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.security.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
        @Query(value = """
                        SELECT p.* FROM products p
                        LEFT JOIN products_variant v ON p.id = v.product_id
                        GROUP BY p.id
                        ORDER BY SUM(v.stock) ASC
                        LIMIT 8
                        """, nativeQuery = true)
        List<Product> findTop8ByOrderByStockDesc();

        @Query(value = """
                        SELECT p.* FROM products p
                        LEFT JOIN products_variant v ON p.id = v.product_id
                        GROUP BY p.id
                        ORDER BY SUM(v.stock) DESC
                        LIMIT 8
                        """, nativeQuery = true)
        List<Product> findTop8ByOrderByStockAsc();

        @Query("SELECT p FROM Product p WHERE p.category.id = (SELECT p2.category.id FROM Product p2 WHERE p2.id = :id) AND p.id <> :id")
        List<Product> getAllProductsRelatedById(@Param("id") int id);

        Page<Product> findByCategory_Slug(String category, Pageable pageable);

        Page<Product> findByNameContaining(String name, Pageable pageable);

        Page<Product> findByCategory_SlugAndNameContaining(String category, String name, Pageable pageable);

}
