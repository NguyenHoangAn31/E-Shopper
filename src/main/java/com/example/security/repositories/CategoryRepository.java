package com.example.security.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}
