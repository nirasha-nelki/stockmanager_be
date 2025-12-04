package com.stockmanager.stockmanager_be.service.impl;

import com.stockmanager.stockmanager_be.constant.CommonMessageConstant;
import com.stockmanager.stockmanager_be.dto.request.CategoryRequestDto;
import com.stockmanager.stockmanager_be.dto.response.CategoryResponseDto;
import com.stockmanager.stockmanager_be.dto.response.ResponseEntityDto;
import com.stockmanager.stockmanager_be.entity.Category;
import com.stockmanager.stockmanager_be.exception.CategoryNotFoundException;
import com.stockmanager.stockmanager_be.exception.DuplicateCategoryException;
import com.stockmanager.stockmanager_be.mapper.CategoryMapper;
import com.stockmanager.stockmanager_be.repo.CategoryRepo;
import com.stockmanager.stockmanager_be.service.CategoryService;
import com.stockmanager.stockmanager_be.type.ResponseStatus;
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
    public ResponseEntityDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = categoryMapper.toCategory(categoryRequestDto);
        Category savedCategory = categoryRepo.save(category);
        return new ResponseEntityDto(ResponseStatus.SUCCESSFUL,"Category created successfully", categoryMapper.toCategoryResponseDto(savedCategory));
    }

    @Override
    public CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto) {
        return null;
    }

    @Override
    public ResponseEntityDto deleteCategory(Integer categoryId) {
        if (!categoryRepo.existsById(categoryId)) {
            throw new CategoryNotFoundException(CommonMessageConstant.COMMON_ERROR_CATEGORY_NOT_FOUND);
        }
        categoryRepo.deleteById(categoryId);
        return new ResponseEntityDto(ResponseStatus.SUCCESSFUL,"Category deleted successfully");
    }

    @Override
    public ResponseEntityDto getCategoryById(Integer categoryId) {

        Optional<Category> category = categoryRepo.findById(categoryId);
        if (category.isEmpty()){
            throw new CategoryNotFoundException(CommonMessageConstant.COMMON_ERROR_CATEGORY_NOT_FOUND);
        }
        CategoryResponseDto categoryResponseDto = categoryMapper.toCategoryResponseDto(category.get());
        return new ResponseEntityDto(ResponseStatus.SUCCESSFUL, "Category fetched successfully", categoryResponseDto);
    }

    @Override
    public ResponseEntityDto getAllCategories() {
        List<CategoryResponseDto> categoryList = categoryRepo.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponseDto)
                .toList();

        return new ResponseEntityDto(ResponseStatus.SUCCESSFUL, "Categories fetched successfully", categoryList);

    }

    @Override
    public CategoryResponseDto findCategoryByName(String categoryName) {
        return null;
//        try {
//            Optional<Category> category = categoryRepo.findByName(categoryName);
//            if (category.isPresent()) {
//                return categoryMapper.toCategoryResponseDto(category.get());
//            } else {
//                throw new CategoryNotFoundException("Category not found with name: " + categoryName);
//            }
//        } catch (CategoryNotFoundException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to get Category " + e.getMessage());
//        }
    }
}
