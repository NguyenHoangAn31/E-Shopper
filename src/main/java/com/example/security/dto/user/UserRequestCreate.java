package com.example.security.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestCreate {

    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid email format!")
    private String email;

    @NotBlank(message = "Name is required!")
    @Size(min = 2, message = "Name must be at least 2 characters!")
    private String name;

    @NotBlank(message = "Password is required!")
    @Size(min = 8, message = "Password must be at least 8 characters!")
    @Pattern(regexp = "^[A-Z].*", message = "Password must start with an uppercase letter!")
    private String password;

}
