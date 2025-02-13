package com.example.security.services.productvariant;

import java.util.List;

import com.example.security.dto.ProductVariant.ProductVariantResponse;

public interface ProductVariantService {
    public List<ProductVariantResponse> getAllProductVariantsBy(int productId);
}
