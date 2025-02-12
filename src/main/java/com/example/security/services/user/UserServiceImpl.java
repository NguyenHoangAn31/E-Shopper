package com.example.security.services.user;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.dto.user.UserRequestCreate;
import com.example.security.dto.user.UserResponse;
import com.example.security.entities.Role;
import com.example.security.entities.User;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public UserResponse getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setEnabled(user.isEnabled());
        response.setPhone(user.getPhone());
        response.setProvider(user.getProvider());
        // Kiểm tra nếu birthday là null
        String birthday = user.getBirthday() != null ? user.getBirthday().toString() : null;
        response.setBirthday(birthday);
        response.setImageUrl(user.getImageUrl());
        response.setWard(user.getWard());
        response.setDistrict(user.getDistrict());
        response.setProvince(user.getProvince());

        return response;
    }

    @Override
    public boolean checkExistAccount(String email) {
        System.out.println("Email: " + email);
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void register(UserRequestCreate dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setEnabled(true);
        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setProvider("LOCAL");
        Role role = roleRepository.findByShortName("USER");
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);

    }

    @Override
    public void lockUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(false);
        userRepository.save(user);

        // Xóa session của người dùng
        if (user.getProvider().equals("GOOGLE")) {
            expireUserSessionsGoogle(user.getEmail());
        } else if (user.getProvider().equals("LOCAL")) {
            expireUserSessionsRegister(user.getEmail());
        }
    }

    private void expireUserSessionsRegister(String username) {
        sessionRegistry.getAllPrincipals().stream()
                .filter(principal -> principal instanceof org.springframework.security.core.userdetails.User)
                .map(principal -> (org.springframework.security.core.userdetails.User) principal)
                .filter(user -> user.getUsername().equals(username))
                .forEach(user -> sessionRegistry.getAllSessions(user, false)
                        .forEach(session -> session.expireNow()));
    }

    private void expireUserSessionsGoogle(String username) {
        sessionRegistry.getAllPrincipals().stream()
                .filter(principal -> {
                    if (principal instanceof org.springframework.security.core.userdetails.User) {
                        return ((org.springframework.security.core.userdetails.User) principal).getUsername()
                                .equals(username);
                    } else if (principal instanceof org.springframework.security.oauth2.core.user.OAuth2User) {
                        return ((org.springframework.security.oauth2.core.user.OAuth2User) principal)
                                .getAttribute("email")
                                .equals(username);
                    }
                    return false;
                })
                .forEach(principal -> sessionRegistry.getAllSessions(principal, false)
                        .forEach(session -> session.expireNow()));
    }

}
