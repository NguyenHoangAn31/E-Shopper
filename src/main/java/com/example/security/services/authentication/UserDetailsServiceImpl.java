package com.example.security.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.security.entities.User;
import com.example.security.repositories.UserRepository;
import com.example.security.services.cart.CartService;
import com.example.security.services.user.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {



    private final UserService userService;

    public UserDetailsServiceImpl(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        User user = userService.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        

        // Trả về một đối tượng UserDetails mới
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                user.getGrantedAuthorities());
    }

}