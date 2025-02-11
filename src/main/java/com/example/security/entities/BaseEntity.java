package com.example.security.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.*;

import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity{

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
