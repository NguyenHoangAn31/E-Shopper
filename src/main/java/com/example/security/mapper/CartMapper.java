package com.example.security.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.security.dto.cart.CartDetailResponse;
import com.example.security.dto.cart.CartRequest;
import com.example.security.entities.Cart;
import com.example.security.entities.CartDetail;
import com.example.security.entities.ProductVariant;
import com.example.security.entities.User;

@Mapper
public interface CartMapper {
    // @Mapping(target = "user", expression = "java(mapUser(request.getUser_id()))")
    Cart toEntity(CartRequest request);

    default Cart toEntityWithDetails(CartRequest request) {
        Cart cart = toEntity(request); // Gọi phương thức toEntity được MapStruct tạo ra
        cart.setCartDetails(mapCartDetails(request.getProducts(), cart)); // Gán order vào orderDetails
        return cart;
    }

    default List<CartDetail> mapCartDetails(List<CartDetailResponse> products, Cart cart) {
        return products.stream()
                .map(product -> {
                    CartDetail detail = new CartDetail();
                    detail.setQuantity(product.getQuantity());
                    detail.setPrice(product.getPrice());
                    detail.setProductVariant(mapProductVariant(product.getId()));
                    detail.setCart(cart);
                    return detail;
                })
                .collect(Collectors.toList());
    }

     default ProductVariant mapProductVariant(int productId) {
        ProductVariant variant = new ProductVariant();
        variant.setId(productId);
        return variant;
    }

    //  default User mapUser(int userId) {
    //     User user = new User();
    //     user.setId(userId);
    //     return user;
    // }

}
