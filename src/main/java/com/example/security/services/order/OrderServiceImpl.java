package com.example.security.services.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.dto.cart.CartDetailResponse;
import com.example.security.dto.order.OrderRequest;
import com.example.security.entities.Order;
import com.example.security.entities.ProductVariant;
import com.example.security.entities.User;
import com.example.security.mapper.OrderMapper;
import com.example.security.repositories.OrderRepository;
import com.example.security.repositories.ProductVariantRepositry;
import com.example.security.repositories.UserRepository;
import com.example.security.services.cart.CartService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductVariantRepositry productVariantRepositry;

    @Autowired
    private CartService cartService;

    // @Autowired
    // private CartRepository cartRepository;

    @Override
    public boolean checkoutProcess(OrderRequest orderRequest) {
        try {
            Order o = orderMapper.toEntityWithDetails(orderRequest);
            User u = userRepository.findByEmail(orderRequest.getEmail()).orElse(null);
            o.setUser(u);

            orderRepository.save(o);
            for (CartDetailResponse p : orderRequest.getProducts()) {
                ProductVariant pv = productVariantRepositry.findById(p.getId()).get();
                pv.setStock(pv.getStock() - p.getQuantity());
                productVariantRepositry.save(pv);
            }
            cartService.deleteCartByEmail(orderRequest.getEmail());
            return true;

        } catch (Exception e) {
            return false;
        }
    }

}
