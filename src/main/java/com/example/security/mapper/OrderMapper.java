package com.example.security.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.security.dto.cart.ProductCartDetailResponse;
import com.example.security.dto.order.OrderRequest;
import com.example.security.entities.Order;
import com.example.security.entities.OrderDetail;
import com.example.security.entities.ProductVariant;
import com.example.security.entities.User;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "id", ignore = true) // ID tự động sinh
    @Mapping(target = "orderDateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = "PENDING") // Trạng thái mặc định
    @Mapping(target = "user", expression = "java(mapUser(request.getUser_id()))")
    Order toEntity(OrderRequest request);


    default Order toEntityWithDetails(OrderRequest request) {
        Order order = toEntity(request); // Gọi phương thức toEntity được MapStruct tạo ra
        order.setOrderDetails(mapOrderDetails(request.getProducts(), order)); // Gán order vào orderDetails
        return order;
    }

  
    default User mapUser(int userId) {
        if (userId == 0)
            return null;
        User user = new User();
        user.setId(userId);
        return user;
    }

    default List<OrderDetail> mapOrderDetails(List<ProductCartDetailResponse> products, Order order) {
        return products.stream()
                .map(product -> {
                    OrderDetail detail = new OrderDetail();
                    detail.setQuantity(product.getQuantity());
                    detail.setPrice(product.getPrice());
                    detail.setProductVariant(mapProductVariant(product.getId()));
                    detail.setOrder(order); // Gán order để tránh null
                    return detail;
                })
                .collect(Collectors.toList());
    }

    default ProductVariant mapProductVariant(int productId) {
        ProductVariant variant = new ProductVariant();
        variant.setId(productId);
        return variant;
    }
}
