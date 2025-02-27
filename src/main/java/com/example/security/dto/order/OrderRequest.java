package com.example.security.dto.order;

import java.util.List;

import com.example.security.dto.cart.CartDetailResponse;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
  
    @NotBlank(message = "Name is required.")
    private String name;
    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;
    @NotBlank(message = "Phone is required.")
    private String phone;
    @NotBlank(message = "Address is required.")
    private String address;
    @NotBlank(message = "Ward is required.")
    private String ward;
    @NotBlank(message = "District is required.")
    private String district;
    @NotBlank(message = "Province is required.")
    private String province;
    @NotBlank(message = "Description is required.")
    private String description;

    private String paymentMethod;
    private double totalPrice;
    List<CartDetailResponse> products;

}
