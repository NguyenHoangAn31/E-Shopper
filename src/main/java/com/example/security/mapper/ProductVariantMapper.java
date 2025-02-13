package com.example.security.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.example.security.dto.ProductVariant.ProductVariantResponse;
import com.example.security.entities.ProductImage;
import com.example.security.entities.ProductVariant;

@Mapper
public interface ProductVariantMapper {

    @Mappings({
            @Mapping(source = "images", target = "images", qualifiedByName = "mapImagesToUrls")
    })
    ProductVariantResponse toResponse(ProductVariant productVariant);

    @Named("mapImagesToUrls")
    default List<String> mapImagesToUrls(List<ProductImage> images) {
        return images.stream().map(ProductImage::getPath).collect(Collectors.toList());
    }
}