package com.example.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    @NotBlank(message = "Email is required!")
    private String email;

    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Password is required!")
    private String password;

}
