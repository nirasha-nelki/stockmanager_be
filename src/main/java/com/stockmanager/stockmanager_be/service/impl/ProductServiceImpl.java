package com.stockmanager.stockmanager_be.service.impl;

import com.stockmanager.stockmanager_be.dto.ProductCreateDto;
import com.stockmanager.stockmanager_be.dto.ProductResponseDto;
import com.stockmanager.stockmanager_be.dto.ProductUpdateDto;
import com.stockmanager.stockmanager_be.entity.Category;
import com.stockmanager.stockmanager_be.entity.Product;
import com.stockmanager.stockmanager_be.exception.CategoryNotFoundException;
import com.stockmanager.stockmanager_be.exception.ProductNotFoundException;
import com.stockmanager.stockmanager_be.mapper.ProductMapper;
import com.stockmanager.stockmanager_be.repo.CategoryRepo;
import com.stockmanager.stockmanager_be.repo.ProductRepo;
import com.stockmanager.stockmanager_be.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final CategoryRepo categoryRepo;

    public ProductServiceImpl(ProductRepo productRepo, ProductMapper productMapper, CategoryRepo categoryRepo){
        this.productRepo = productRepo;
        this.productMapper = productMapper;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public ProductResponseDto createProduct(ProductCreateDto productCreateDto) {
        Product product = productMapper.toProduct(productCreateDto);

        Category category = categoryRepo.findById(productCreateDto.getCategoryId())
                        .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        product.setCategory(category);

        Product saved = productRepo.save(product);
        return productMapper.toProductResponseDto(saved);
    }

    @Override
    public ProductResponseDto updateProduct(ProductUpdateDto productUpdateDto) {
        try {

            Product product = productRepo.findById(productUpdateDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            productMapper.updateProductFromDto(productUpdateDto, product);

            Category category = categoryRepo.findById(productUpdateDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
            product.setCategory(category);

            Product updatedProduct = productRepo.save(product);
            return productMapper.toProductResponseDto(updatedProduct);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product " + e.getMessage() );
        }
    }

    @Override
    public ProductResponseDto getProductById(int productId) {
        try{
            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));
            ProductResponseDto productResponseDto = productMapper.toProductResponseDto(product);
            productResponseDto.setCategoryId(product.getCategory().getCategoryId());
            productResponseDto.setCategoryName(product.getCategory().getName());

            return productResponseDto;
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException("Failed to get product " + e.getMessage() );
        }
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<ProductResponseDto> productList = new ArrayList<>();
        ProductResponseDto productResponseDto = new ProductResponseDto();
        try {
            List<Product> products = productRepo.findAll();

            for (Product product : products) {

                productResponseDto = productMapper.toProductResponseDto(product);
                productResponseDto.setCategoryId(product.getCategory().getCategoryId());
                productResponseDto.setCategoryName(product.getCategory().getName());
                productList.add(productResponseDto);
            }
            return productList;

        } catch (Exception e) {
            throw new RuntimeException("Failed to get products " + e.getMessage() );
        }
    }

    @Override
    public int deleteProduct(int productId) {
        try{
            productRepo.deleteById(productId);
            return 1;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product " + e.getMessage() );
        }
    }

    @Override
    public int saveProductList(List<ProductCreateDto> productCreateDtoList) {

        Category category = new Category();
        Product product = new Product();

        try {
            for (ProductCreateDto productCreateDto : productCreateDtoList) {
                product = productMapper.toProduct(productCreateDto);
                category = categoryRepo.findById(productCreateDto.getCategoryId())
                        .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
                product.setCategory(category);

                productRepo.save(product);
            }
            return 1;
        } catch (CategoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save products " + e.getMessage() );
        }
    }
}
