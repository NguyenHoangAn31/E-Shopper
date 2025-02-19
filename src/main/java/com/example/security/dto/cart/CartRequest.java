package com.example.security.dto.cart;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {
    private String email;
    private List<CartDetailResponse> products;
}
