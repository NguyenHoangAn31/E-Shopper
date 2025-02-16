package com.example.security.services.order;

import com.example.security.dto.order.OrderRequest;

public interface OrderService {
    public boolean checkoutProcess(OrderRequest orderRequest);
}
