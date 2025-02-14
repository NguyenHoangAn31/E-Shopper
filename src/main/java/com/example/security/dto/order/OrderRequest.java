package com.example.security.dto.order;

import java.util.List;

import com.example.security.dto.cart.ProductCartDetailResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private int user_id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String ward;
    private String district;
    private String province;
    private String paymentMethod;
    List<ProductCartDetailResponse> products;

}
