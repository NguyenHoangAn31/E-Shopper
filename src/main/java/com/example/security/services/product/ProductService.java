package com.example.security.services.product;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.security.dto.product.ProductResponse;

public interface ProductService {
     public Page<ProductResponse> getAllProducts(int page, int size, String category, String search, String sort);
     public List<ProductResponse> getAllProductsRelatedById(int id);
     public ProductResponse getProductById(int id);
     public List<ProductResponse> getTop8Trandy();
     public List<ProductResponse> getTop8JustArrived();
}
