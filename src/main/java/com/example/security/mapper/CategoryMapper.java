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

    @Mapping(source = "products", target = "productCount", qualifiedByName = "getProductCount")
    CategoryResponse categoryToResponse(Category category);

    @Named("getProductCount")
    default int getProductCount(List<Product> products) {
        return products != null ? products.size() : 0;
    }
}
