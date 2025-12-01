package com.stockmanager.stockmanager_be.service.impl;

import com.stockmanager.stockmanager_be.dto.CategoryRequestDto;
import com.stockmanager.stockmanager_be.dto.CategoryResponseDto;
import com.stockmanager.stockmanager_be.entity.Category;
import com.stockmanager.stockmanager_be.exception.CategoryNotFoundException;
import com.stockmanager.stockmanager_be.exception.DuplicateCategoryException;
import com.stockmanager.stockmanager_be.mapper.CategoryMapper;
import com.stockmanager.stockmanager_be.repo.CategoryRepo;
import com.stockmanager.stockmanager_be.service.CategoryService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepo categoryRepo, CategoryMapper categoryMapper) {
        this.categoryRepo = categoryRepo;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        try {
            Category category = categoryMapper.toCategory(categoryRequestDto);

            Category newCategory = categoryRepo.save(category);
            return categoryMapper.toCategoryResponseDto(newCategory);
        } catch (DataIntegrityViolationException e){
            throw new DuplicateCategoryException("Category with name '" + categoryRequestDto.getName() + "' already exists");
        } catch (Exception e) {
            throw new RuntimeException("Failed to create category " + e.getMessage() );
        }
    }

    @Override
    public CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto) {
        return null;
    }

    @Override
    public void deleteCategory(Integer categoryId) {

        try {
            categoryRepo.deleteById(categoryId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete category " + e.getMessage() );
        }
    }

    @Override
    public CategoryResponseDto getCategoryById(Integer categoryId) {
        try {
            Optional<Category> category = categoryRepo.findById(categoryId);
            if (category.isPresent()) {
                return categoryMapper.toCategoryResponseDto(category.get());
            } else {
                throw new CategoryNotFoundException("Category not found with id: " + categoryId);
            }
        } catch (CategoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Category " + e.getMessage());
        }
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<CategoryResponseDto> categoryList = new ArrayList<>();
        try {
            List<Category> categories = categoryRepo.findAll();

            for (Category category : categories) {
                CategoryResponseDto categoryResponseDto = categoryMapper.toCategoryResponseDto(category);
                categoryList.add(categoryResponseDto);
            }

            return categoryList;

        } catch (Exception e) {
            throw new RuntimeException("Failed to get all categories " + e.getMessage() );
        }
    }
}
