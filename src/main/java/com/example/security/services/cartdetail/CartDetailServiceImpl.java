package com.example.security.services.cartdetail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entities.CartDetail;
import com.example.security.repositories.CartDetailRepository;
import com.example.security.services.cart.CartService;

import jakarta.transaction.Transactional;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private CartService cartService;

    @Transactional
    @Override
    public Map<String, Object> cartAction(String action, int id) {
        Map<String, Object> result = new HashMap<>();
        CartDetail cartDetail = cartDetailRepository.findById(id).orElse(null);

        if (cartDetail == null) {
            result.put("status", "error");
            result.put("message", "Cart item not found");
            return result;
        }

        switch (action) {
            case "delete":
                cartDetailRepository.delete(cartDetail);
                result.put("status", "deleted");
                result.put("countcard", cartService.getCountCartByEmail(cartDetail.getCart().getUser().getEmail()));
                return result;
            case "plus":
                cartDetail.setQuantity(cartDetail.getQuantity() + 1);
                cartDetailRepository.save(cartDetail);
                result.put("status", "plus");
                result.put("quantity", cartDetail.getQuantity());
                return result;
            case "min":
                if (cartDetail.getQuantity() > 1) {
                    cartDetail.setQuantity(cartDetail.getQuantity() - 1);
                    cartDetailRepository.save(cartDetail);
                    result.put("status", "min");
                    result.put("quantity", cartDetail.getQuantity());
                } else {
                    cartDetailRepository.delete(cartDetail);
                    result.put("status", "deleted");
                    result.put("countcard", cartService.getCountCartByEmail(cartDetail.getCart().getUser().getEmail()));

                }
                return result;
            default:
                result.put("status", "error");
                result.put("message", "Invalid action");
                return result;
        }
    }

}
