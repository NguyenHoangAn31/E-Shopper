package com.example.security.services.productvariant;

import java.util.List;

import com.example.security.dto.ProductVariant.ProductVariantResponse;
import com.example.security.entities.ProductVariant;

public interface ProductVariantService {
    public ProductVariant findById(int id);
    public void save(ProductVariant productVariant);
    public List<ProductVariantResponse> getAllProductVariantsBy(int productId);
}
