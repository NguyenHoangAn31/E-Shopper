package com.example.security.dto.ProductVariant;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantResponse {
     private int id;

    private double price;

    private int stock;

    private String size;

    private String color;

    private List<String> images;
}
