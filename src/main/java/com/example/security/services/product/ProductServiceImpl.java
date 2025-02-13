package com.example.security.services.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.security.dto.product.ProductResponse;
import com.example.security.entities.Product;
import com.example.security.mapper.ProductMapper;
import com.example.security.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Page<ProductResponse> getAllProducts(int page, int size, String category) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage;
        if (category.equals("all")) {
            productPage = productRepository.findAll(pageable);
        } else {
            productPage = productRepository.findByCategory_Name(category, pageable);
        }

        return productPage.map(productMapper::productToResponse);
    }

    @Override
    public List<ProductResponse> getAllProductsRelatedById(int id) {
        List<Product> products = productRepository.getAllProductsRelatedById(id);

        return products.stream()
                .map(productMapper::productToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        return productMapper.productToResponse(product);
    }

    @Override
    public List<ProductResponse> getTop8Trandy() {
        List<Product> products = productRepository.findTop8ByOrderByStockDesc();
        return products.stream()
                .map(product -> productMapper.productToResponse(product))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getTop8JustArrived() {
        List<Product> products = productRepository.findTop8ByOrderByStockAsc();
        return products.stream()
                .map(product -> productMapper.productToResponse(product))
                .collect(Collectors.toList());
    }
}
