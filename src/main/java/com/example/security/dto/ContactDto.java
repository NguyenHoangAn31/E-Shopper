package com.example.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {
    @NotBlank(message = "Name is required!")
    String name;
    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid email format!")
    String email;
    @NotBlank(message = "Subject is required!")
    String subject;
    @NotBlank(message = "Message is required!")
    String message;
}
