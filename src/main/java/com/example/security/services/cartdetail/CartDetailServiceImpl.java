package com.example.security.services.cartdetail;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.dto.cart.ProductCartDetailResponse;
import com.example.security.mapper.CartDetailMapper;
import com.example.security.repositories.CartDetailRepository;
import com.example.security.repositories.CartRepository;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private CartDetailMapper cartDetailMapper;

}
