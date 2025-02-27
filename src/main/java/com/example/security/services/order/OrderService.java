package com.example.security.services.order;

import com.example.security.dto.order.OrderRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface OrderService {
    public boolean checkoutProcess(OrderRequest orderRequest, HttpServletRequest request);
}
