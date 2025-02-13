package com.example.security.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.security.dto.product.ProductResponse;
import com.example.security.entities.Product;
import com.example.security.entities.ProductVariant;

@Mapper
public interface ProductMapper {

    @Mapping(source = "category.name", target = "categoryName")
    // @Mapping(source = "variants", target = "price", qualifiedByName = "getFirstVariantPrice")
    @Mapping(source = "variants", target = "imageUrl", qualifiedByName = "getFirstVariantImage")
    ProductResponse productToResponse(Product product);

    // @Named("getFirstVariantPrice")
    // default double getFirstVariantPrice(List<ProductVariant> variants) {
    //     return (variants != null && !variants.isEmpty()) ? variants.get(0).getPrice() : 0.0;
    // }

    @Named("getFirstVariantImage")
    default String getFirstVariantImage(List<ProductVariant> variants) {
        if (variants != null && !variants.isEmpty() && variants.get(0).getImages() != null
                && !variants.get(0).getImages().isEmpty()) {
            return variants.get(0).getImages().get(0).getPath();
        }
        return null; 
    }
}
