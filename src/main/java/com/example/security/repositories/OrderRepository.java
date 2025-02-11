package com.example.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    
}
