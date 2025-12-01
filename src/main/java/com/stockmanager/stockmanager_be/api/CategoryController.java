package com.stockmanager.stockmanager_be.api;

import com.stockmanager.stockmanager_be.dto.CategoryRequestDto;
import com.stockmanager.stockmanager_be.dto.CategoryResponseDto;
import com.stockmanager.stockmanager_be.exception.CategoryNotFoundException;
import com.stockmanager.stockmanager_be.service.impl.CategoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add_category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        try{
            CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto);
            return ResponseEntity.ok(categoryResponseDto);
//            return ResponseEntity.ok(categoryService.createCategory(categoryRequestDto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getACategory(@PathVariable int id) {
        try {
            CategoryResponseDto categoryResponseDto = categoryService.getCategoryById(id);
            return ResponseEntity.ok(categoryResponseDto);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/get_all")
    public ResponseEntity<?> getAllCategories() {
        try {
            return ResponseEntity.ok(categoryService.getAllCategories());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
