package com.example.security.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private int id;
    private String name;
    private boolean status;
    private String imageUrl;
    private int productCount;
}
