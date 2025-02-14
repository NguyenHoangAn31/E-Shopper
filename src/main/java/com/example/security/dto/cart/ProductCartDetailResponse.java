package com.example.security.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCartDetailResponse {
    private int id;
    private String imageUrl;
    private double price;
    private int quantity;
}
