package com.example.security.services.cart;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.dto.cart.CartDetailResponse;
import com.example.security.entities.Cart;
import com.example.security.entities.CartDetail;
import com.example.security.entities.ProductVariant;
import com.example.security.entities.User;
import com.example.security.repositories.CartRepository;
import com.example.security.services.productvariant.ProductVariantService;
import com.example.security.services.user.UserService;


@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductVariantService productVariantService;

    @Autowired
    private UserService userService;

    @Override
    public void saveCart(int user_id, List<CartDetailResponse> cartDetails) {

        // Check if the cart already exists for the user
        Optional<Cart> existingCartOptional = cartRepository.findByUserId(user_id);
        Cart cart;

        // If cart exists, use it; otherwise, create a new one
        if (existingCartOptional.isPresent()) {
            cart = existingCartOptional.get();

            // Loop through the new cart details
            for (CartDetailResponse newDetail : cartDetails) {
                // Check if product already exists in the cart
                boolean productExists = false;
                for (CartDetail existingDetail : cart.getCartDetails()) {
                    if (existingDetail.getProductVariant().getId() == newDetail.getProductVariant_id()) {
                        // Product exists, so increase the quantity
                        existingDetail.setQuantity(existingDetail.getQuantity() + newDetail.getQuantity());
                        productExists = true;
                        break;
                    }
                }

                // If product doesn't exist, add it to the cart
                if (!productExists) {
                    CartDetail newCartDetail = new CartDetail();
                    newCartDetail.setQuantity(newDetail.getQuantity());
                    newCartDetail.setPrice(newDetail.getPrice());
                    // Assuming you have a method to get the ProductVariant by ID
                    ProductVariant productVariant = productVariantService.findById(newDetail.getProductVariant_id());
                    newCartDetail.setProductVariant(productVariant);
                    newCartDetail.setCart(cart);
                    cart.getCartDetails().add(newCartDetail);
                }
            }

        } else {
            // If no cart exists, create a new cart and save it
            cart = new Cart();
            User user = userService.findById(user_id);
            cart.setUser(user);

            // Convert the CartRequest to CartDetail entities
            for (CartDetailResponse newDetail : cartDetails) {
                CartDetail cartDetail = new CartDetail();
                cartDetail.setQuantity(newDetail.getQuantity());
                cartDetail.setPrice(newDetail.getPrice());
                ProductVariant productVariant = productVariantService.findById(newDetail.getProductVariant_id());
                cartDetail.setProductVariant(productVariant);
                cartDetail.setCart(cart);
                cart.getCartDetails().add(cartDetail);
            }
        }

        // Save the cart
        cartRepository.save(cart);
    }

    @Override
    public int getCountCartByEmail(String email) {
        Integer count = cartRepository.countCartByEmail(email);
        return count != null ? count : 0;
    }

    @Override
    public List<CartDetailResponse> getCartByEmail(String email) {
        List<CartDetailResponse> cartDetails = new ArrayList<>();
        List<CartDetail> cartDetailsList = cartRepository.getCartByEmail(email);
        for (CartDetail cartDetail : cartDetailsList) {
            CartDetailResponse cartDetailResponse = new CartDetailResponse();
            cartDetailResponse.setId(cartDetail.getId());
            cartDetailResponse.setProductVariant_id(cartDetail.getProductVariant().getId());
            cartDetailResponse.setImageUrl(cartDetail.getProductVariant().getImages().get(0).getPath());
            cartDetailResponse.setPrice(cartDetail.getPrice());
            cartDetailResponse.setName(cartDetail.getProductVariant().getProduct().getName());
            cartDetailResponse.setSize(cartDetail.getProductVariant().getSize());
            cartDetailResponse.setColor(cartDetail.getProductVariant().getColor());
            cartDetailResponse.setQuantity(cartDetail.getQuantity());
            cartDetails.add(cartDetailResponse);
        }
        return cartDetails;

    }

    @Override
    public void deleteCartByEmail(String email) {
        Cart cart = cartRepository.findByUserEmail(email).get();
        if (cart != null) {
            cart.getCartDetails().clear(); // Xóa tham chiếu cartDetails trước
            cartRepository.delete(cart); // Sau đó mới xóa cart
        }
    }
}
