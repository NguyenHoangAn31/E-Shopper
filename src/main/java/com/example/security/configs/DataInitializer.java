package com.example.security.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.security.entities.Category;
import com.example.security.entities.Role;
import com.example.security.repositories.CategoryRepository;
import com.example.security.repositories.RoleRepository;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initializeRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role(1, "ROLE_ADMIN", "ADMIN", null));
                roleRepository.save(new Role(2, "ROLE_USER  ", "USER", null));
            }
        };
    }
    @Bean
    public CommandLineRunner initializeCategories(CategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category(1, "Dress", true));
                categoryRepository.save(new Category(2, "Shirts", true));
                categoryRepository.save(new Category(3, "Jeans", true));
                categoryRepository.save(new Category(4, "Swimwear", true));
                categoryRepository.save(new Category(5, "Sleepwear", true));
                categoryRepository.save(new Category(6, "Sportswear", true));
                categoryRepository.save(new Category(7, "Jackets", true));
                categoryRepository.save(new Category(8, "Shoes", true));
            }
        };
    }

}
