package com.stockmanager.stockmanager_be.api;

import com.stockmanager.stockmanager_be.constant.ApiUriConstants;
import com.stockmanager.stockmanager_be.dto.request.CategoryRequestDto;
import com.stockmanager.stockmanager_be.dto.response.CategoryResponseDto;
import com.stockmanager.stockmanager_be.dto.response.ResponseEntityDto;
import com.stockmanager.stockmanager_be.exception.CategoryNotFoundException;
import com.stockmanager.stockmanager_be.service.impl.CategoryServiceImpl;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToUrl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(ApiUriConstants.CATEGORY_ADD)
    public ResponseEntity<ResponseEntityDto> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        ResponseEntityDto responseEntityDto = categoryService.createCategory(categoryRequestDto);
        return new ResponseEntity<>(responseEntityDto, HttpStatus.OK);
    }

    @GetMapping(ApiUriConstants.CATEGORY_GET)
    public ResponseEntity<ResponseEntityDto> getACategory(@PathVariable int id) {
        ResponseEntityDto responseEntityDto = categoryService.getCategoryById(id);
        return new ResponseEntity<>(responseEntityDto, HttpStatus.OK);
    }

    @GetMapping(ApiUriConstants.CATEGORY_GET_ALL)
    public ResponseEntity<ResponseEntityDto> getAllCategories() {
        ResponseEntityDto responseEntityDto = categoryService.getAllCategories();
        return new ResponseEntity<>(responseEntityDto, HttpStatus.OK);
    }

    @DeleteMapping(ApiUriConstants.CATEGORY_DELETE)
    public ResponseEntity<ResponseEntityDto> deleteCategory(@PathVariable int id) {
      ResponseEntityDto responseEntityDto = categoryService.deleteCategory(id);
        return new ResponseEntity<>(responseEntityDto, HttpStatus.OK);
    }
}
