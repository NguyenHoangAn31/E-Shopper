package com.example.security.controllers.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.entities.Role;
import com.example.security.entities.User;
import com.example.security.repositories.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/test")
public class TestRestController {

    @Autowired
    UserRepository userRepository;
    @GetMapping("/test1")
    public User getMethodName() {
        User user = userRepository.findById(1).get();
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            System.out.println(role.getName());
        }
        user.setRoles(roles);

        return user;
    }
    
}
