package com.example.security.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.security.entities.Cart;
import com.example.security.entities.CartDetail;

import io.lettuce.core.dynamic.annotation.Param;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT COUNT(cd.id) FROM Cart c JOIN c.cartDetails cd WHERE c.user.email = :email")
    Integer countCartByEmail(@Param("email") String email);

    Optional<Cart> findByUserId(int userId);
    Optional<Cart> findByUserEmail(String email);

    @Query("SELECT cd FROM CartDetail cd WHERE cd.cart.user.email = :email")
    List<CartDetail> getCartByEmail(@Param("email") String email);
}
