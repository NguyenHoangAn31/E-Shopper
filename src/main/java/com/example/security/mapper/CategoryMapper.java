package com.example.security.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.security.dto.category.CategoryResponse;
import com.example.security.entities.Category;
import com.example.security.entities.Product;

@Mapper
public interface CategoryMapper {

    @Mapping(source = "products", target = "productCount", qualifiedByName = "getProductVariantCount")
    CategoryResponse categoryToResponse(Category category);

    @Named("getProductVariantCount")
    default int getProductVariantCount(List<Product> products) {
        if (products == null) return 0;

        return products.stream()
                .mapToInt(product -> product.getVariants() != null ? product.getVariants().size() : 0)
                .sum();
    }
}
