package com.stockmanager.stockmanager_be.service;

import com.stockmanager.stockmanager_be.dto.CategoryRequestDto;
import com.stockmanager.stockmanager_be.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto);
    void deleteCategory(Integer categoryId);
    CategoryResponseDto getCategoryById(Integer categoryId);
    List<CategoryResponseDto> getAllCategories();
}
