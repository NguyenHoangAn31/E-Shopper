package com.example.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.example.security.dto.product.ProductResponse;
import com.example.security.entities.Product;
import com.example.security.entities.ProductImage;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ProductMapper {

    @Mappings({
            @Mapping(source = "category.name", target = "categoryName"),
            @Mapping(target = "images", expression = "java(mapProductImages(product.getImages()))")
    })
    ProductResponse productToResponse(Product product);

    default List<String> mapProductImages(List<ProductImage> productImages) {
        return productImages.stream()
                .map(ProductImage::getPath) 
                .collect(Collectors.toList());
    }
}
