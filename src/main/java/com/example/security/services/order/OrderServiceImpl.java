package com.example.security.services.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.dto.order.OrderRequest;
import com.example.security.mapper.OrderMapper;
import com.example.security.repositories.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean checkoutProcess(OrderRequest orderRequest) {
        try {
            System.out.println("check out process: " + orderRequest);

            orderRepository.save(orderMapper.toEntityWithDetails(orderRequest));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
