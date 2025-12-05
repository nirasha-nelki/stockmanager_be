package com.stockmanager.stockmanager_be.service;

import com.stockmanager.stockmanager_be.dto.request.CategoryRequestDto;
import com.stockmanager.stockmanager_be.dto.response.CategoryResponseDto;
import com.stockmanager.stockmanager_be.dto.response.ResponseEntityDto;

import java.util.List;

public interface CategoryService {

    ResponseEntityDto createCategory(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto);
    ResponseEntityDto deleteCategory(Integer categoryId);
    ResponseEntityDto getCategoryById(Integer categoryId);
    ResponseEntityDto getAllCategories();
    CategoryResponseDto findCategoryByName(String categoryName);
}
