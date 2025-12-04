package com.stockmanager.stockmanager_be.mapper;

import com.stockmanager.stockmanager_be.dto.request.CategoryRequestDto;
import com.stockmanager.stockmanager_be.dto.response.CategoryResponseDto;
import com.stockmanager.stockmanager_be.dto.CategoryUpdateDto;
import com.stockmanager.stockmanager_be.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    Category toCategory(CategoryRequestDto categoryRequestDto);

    @Mapping(source = "categoryId", target = "categoryId")
    Category toCategory(CategoryUpdateDto categoryUpdateDto);

    CategoryResponseDto toCategoryResponseDto(Category category);
}
