package com.example.security.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailResponse {
    private int id;
    private String imageUrl;
    private String name;
    private String size;
    private String color;
    private double price;
    private int quantity;
}
