package com.stockmanager.stockmanager_be.mapper;

import com.stockmanager.stockmanager_be.dto.ProductCreateDto;
import com.stockmanager.stockmanager_be.dto.ProductResponseDto;
import com.stockmanager.stockmanager_be.dto.ProductUpdateDto;
import com.stockmanager.stockmanager_be.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    // For create
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toProduct(ProductCreateDto productCreateDto);
    ProductCreateDto toProductCreateDto(Product product);

    // For update - update existing entity
    @Mapping(target = "category", ignore = true)
    void updateProductFromDto(ProductUpdateDto dto, @MappingTarget Product entity);

    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "category.categoryId", target = "categoryId")
    ProductResponseDto toProductResponseDto(Product product);
}
