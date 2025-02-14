package com.example.security.services.category;

import java.util.List;

import com.example.security.dto.category.CategoryResponse;

public interface CategoryService {
    public List<CategoryResponse> getAllCategories();
}
