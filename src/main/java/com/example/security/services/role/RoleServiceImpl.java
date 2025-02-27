package com.example.security.services.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entities.Role;
import com.example.security.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findByShortName(String name){
        return roleRepository.findByShortName(name);
    }
}
