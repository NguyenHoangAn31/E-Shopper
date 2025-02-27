package com.example.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entities.PasswordResetToken;
import com.example.security.entities.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    Optional<PasswordResetToken> findByToken(String token);
    PasswordResetToken findByUser(User user);


}
