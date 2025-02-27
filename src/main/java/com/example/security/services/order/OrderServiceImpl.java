package com.example.security.services.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.security.dto.cart.CartDetailResponse;
import com.example.security.dto.order.OrderRequest;
import com.example.security.entities.Order;
import com.example.security.entities.ProductVariant;
import com.example.security.entities.User;
import com.example.security.mapper.OrderMapper;
import com.example.security.repositories.OrderRepository;
import com.example.security.services.cart.CartService;
import com.example.security.services.productvariant.ProductVariantService;
import com.example.security.services.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductVariantService productVariantService;

    @Override
    public boolean checkoutProcess(OrderRequest orderRequest, HttpServletRequest request) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        try {

            Order o = orderMapper.toEntityWithDetails(orderRequest);
            User u = userService.findByEmail(currentUsername);
            o.setUser(u);

            orderRepository.save(o);
            for (CartDetailResponse p : orderRequest.getProducts()) {
                ProductVariant pv = productVariantService.findById(p.getProductVariant_id());
                pv.setStock(pv.getStock() - p.getQuantity());
                productVariantService.save(pv);
            }
            HttpSession session = request.getSession();
            session.removeAttribute("cart");
            if (u != null) {
                cartService.deleteCartByEmail(orderRequest.getEmail());
            }
            return true;

        } catch (Exception e) {
            return false;
        }
    }

}
