package com.example.security.services.productvariant;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.dto.ProductVariant.ProductVariantResponse;
import com.example.security.entities.ProductVariant;
import com.example.security.mapper.ProductVariantMapper;
import com.example.security.repositories.ProductVariantRepositry;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {
    @Autowired
    private ProductVariantRepositry productVariantRepositry;

    @Autowired
    private ProductVariantMapper productVariantMapper;

    @Override
    public ProductVariant findById(int id){
        return productVariantRepositry.findById(id).get();
    }

    @Override
    public void save(ProductVariant productVariant){
        productVariantRepositry.save(productVariant);
    }

    @Override
    public List<ProductVariantResponse> getAllProductVariantsBy(int productId) {
        return productVariantRepositry.findByProductId(productId)
                .stream()
                .map(productVariantMapper::toResponse)
                .collect(Collectors.toList());
    }
}
