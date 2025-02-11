package com.example.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    
}
