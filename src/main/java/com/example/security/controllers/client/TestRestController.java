package com.example.security.controllers.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.entities.Role;
import com.example.security.entities.User;
import com.example.security.repositories.CartRepository;
import com.example.security.repositories.UserRepository;
import com.example.security.services.cart.CartService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/test")
public class TestRestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private CartService cartService;


  

}
