package com.example.security.controllers.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.entities.Role;
import com.example.security.entities.User;
import com.example.security.repositories.UserRepository;

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

    @PostMapping("/upload-binary")
    public String uploadBinaryFile(@RequestBody byte[] fileBytes) {
        try {
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Đặt tên file cố định hoặc từ query param nếu muốn
            Path filePath = uploadPath.resolve("uploaded_file.png");
            Files.write(filePath, fileBytes);

            return "Binary file uploaded successfully: " + filePath.toString();
        } catch (IOException e) {
            return "Binary file upload failed: " + e.getMessage();
        }
    }

}
