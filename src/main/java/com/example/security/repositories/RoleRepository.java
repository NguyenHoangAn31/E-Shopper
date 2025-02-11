package com.example.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByShortName(String shortName);

}
