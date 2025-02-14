package com.example.security.services.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.dto.category.CategoryResponse;
import com.example.security.entities.Category;
import com.example.security.mapper.CategoryMapper;
import com.example.security.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        // Map each Category entity to CategoryResponse using the CategoryMapper
        return categories.stream()
                .map(category -> categoryMapper.categoryToResponse(category))
                .collect(Collectors.toList());
    }
}
