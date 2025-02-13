package com.example.security.dto.product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
     private int id;

    private String name;

    private String description;

    private boolean status;

    private String categoryName;

    private String imageUrl;

    private double price;

}
