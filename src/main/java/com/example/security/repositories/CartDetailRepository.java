package com.example.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entities.CartDetail;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    
}
