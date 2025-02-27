package com.example.security.services.cart;

import java.util.List;

import com.example.security.dto.cart.CartDetailResponse;

public interface CartService {
    public void saveCart(int user_id, List<CartDetailResponse> cartDetails);
    public int getCountCartByEmail(String email);
    public List<CartDetailResponse> getCartByEmail(String email);
    public void deleteCartByEmail(String email);
    
}
