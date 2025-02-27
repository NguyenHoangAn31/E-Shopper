package com.example.security.services.role;

import com.example.security.entities.Role;

public interface RoleService {

    public Role findByShortName(String name);
    
}
