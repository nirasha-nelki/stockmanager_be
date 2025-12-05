package com.stockmanager.stockmanager_be.service.impl;

import com.stockmanager.stockmanager_be.constant.CommonMessageConstant;
import com.stockmanager.stockmanager_be.dto.*;
import com.stockmanager.stockmanager_be.dto.request.ProductCreateDto;
import com.stockmanager.stockmanager_be.dto.response.ProductResponseDto;
import com.stockmanager.stockmanager_be.dto.response.ResponseEntityDto;
import com.stockmanager.stockmanager_be.entity.Category;
import com.stockmanager.stockmanager_be.entity.Product;
import com.stockmanager.stockmanager_be.exception.CategoryNotFoundException;
import com.stockmanager.stockmanager_be.exception.ProductNotFoundException;
import com.stockmanager.stockmanager_be.mapper.ProductMapper;
import com.stockmanager.stockmanager_be.repo.CategoryRepo;
import com.stockmanager.stockmanager_be.repo.ProductRepo;
import com.stockmanager.stockmanager_be.service.ProductService;
import com.stockmanager.stockmanager_be.type.ResponseStatus;
import com.stockmanager.stockmanager_be.util.MessageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final CategoryRepo categoryRepo;
    private final MessageUtil messageUtil;

    public ProductServiceImpl(ProductRepo productRepo, ProductMapper productMapper, CategoryRepo categoryRepo, MessageUtil messageUtil){
        this.productRepo = productRepo;
        this.productMapper = productMapper;
        this.categoryRepo = categoryRepo;
        this.messageUtil = messageUtil;
    }

    @Override
    public ResponseEntityDto createProduct(ProductCreateDto productCreateDto) {
        Product product = productMapper.toProduct(productCreateDto);

        Category category = categoryRepo.findById(productCreateDto.getCategoryId())
                        .orElseThrow(() -> new CategoryNotFoundException(CommonMessageConstant.COMMON_ERROR_CATEGORY_NOT_FOUND));
        product.setCategory(category);

        Product saved = productRepo.save(product);
        String message = messageUtil.getMessage(CommonMessageConstant.COMMON_SUCCESS_PRODUCT_CREATED);
        return new ResponseEntityDto(ResponseStatus.SUCCESSFUL, message, saved.getProductId());
    }

    @Override
    public ResponseEntityDto updateProduct(ProductUpdateDto productUpdateDto) {

        Optional<Product> product = productRepo.findById(productUpdateDto.getProductId());
        if (product.isEmpty()){
            throw new ProductNotFoundException(CommonMessageConstant.COMMON_ERROR_PRODUCT_NOT_FOUND);
        }
        productMapper.updateProductFromDto(productUpdateDto, product.get());

        Optional<Category> category = categoryRepo.findById(productUpdateDto.getCategoryId());
        if (category.isEmpty()){
            throw new CategoryNotFoundException(CommonMessageConstant.COMMON_ERROR_CATEGORY_NOT_FOUND);
        }
        product.get().setCategory(category.get());
        Product updatedProduct = productRepo.save(product.get());
        String message = messageUtil.getMessage(CommonMessageConstant.COMMON_SUCCESS_PRODUCT_UPDATED);
        return new ResponseEntityDto(ResponseStatus.SUCCESSFUL, message, updatedProduct.getProductId());

    }

    @Override
    public ResponseEntityDto getProductById(int productId) {
        Optional<Product> product = productRepo.findById(productId);
        if (product.isEmpty()){
            throw new ProductNotFoundException(CommonMessageConstant.COMMON_ERROR_PRODUCT_NOT_FOUND);
        }
        ProductResponseDto productResponseDto = productMapper.toProductResponseDto(product.get());
        productResponseDto.setCategoryId(product.get().getCategory().getCategoryId());
        productResponseDto.setCategoryName(product.get().getCategory().getName());
        String message = messageUtil.getMessage(CommonMessageConstant.COMMON_SUCCESS_DATA_RETRIEVED);
        return new ResponseEntityDto(ResponseStatus.SUCCESSFUL, message, productResponseDto);
    }

    @Override
    public ResponseEntityDto getAllProducts() {
        List<ProductResponseDto> productList = categoryRepo.findAll()
                .stream()
                .flatMap(category -> category.getProducts().stream()
                        .map(product -> {
                            ProductResponseDto dto = productMapper.toProductResponseDto(product);
                            dto.setCategoryId(category.getCategoryId());
                            dto.setCategoryName(category.getName());
                            return dto;
                        }))
                .toList();
        String message = messageUtil.getMessage(CommonMessageConstant.COMMON_SUCCESS_LIST_RETRIEVED);
        return new ResponseEntityDto(ResponseStatus.SUCCESSFUL, message, productList);
    }

    @Override
    public ResponseEntityDto deleteProduct(int productId) {

        Optional<Product> product = productRepo.findById(productId);
        if (product.isEmpty()){
            throw new ProductNotFoundException(CommonMessageConstant.COMMON_ERROR_PRODUCT_NOT_FOUND);
        }
        productRepo.deleteById(productId);
        String message = messageUtil.getMessage(CommonMessageConstant.COMMON_SUCCESS_PRODUCT_DELETED);
        return new ResponseEntityDto(ResponseStatus.SUCCESSFUL, message, null);

//        try{
//            productRepo.deleteById(productId);
//            return 1;
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to delete product " + e.getMessage() );
//        }
    }

    @Override
    public ResponseEntityDto saveProductList(List<ProductBulkDto> items) {

        List<Product> products = new ArrayList<>(items.size());

        for (ProductBulkDto dto : items) {

            Category category = categoryRepo.findByName(dto.getCategoryName())
                    .orElseThrow(() -> new CategoryNotFoundException(
                            CommonMessageConstant.COMMON_ERROR_CATEGORY_NOT_FOUND));

            Product p = productMapper.toProductFromBulkDto(dto);
            p.setCategory(category);
            products.add(p);
        }

        productRepo.saveAll(products);
        String message = messageUtil.getMessage(CommonMessageConstant.COMMON_SUCCESS_PRODUCTS_SAVED);
        return new ResponseEntityDto(ResponseStatus.SUCCESSFUL, message, null);
    }


    @Override
    public ResponseEntityDto getProductsPaginated(int page, int size, int categoryId, int status) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<ProductResponseDto> responseDto = productRepo.findProductsFiltered(categoryId, status, pageable);
        String message = messageUtil.getMessage(CommonMessageConstant.COMMON_SUCCESS_LIST_RETRIEVED);
        return new ResponseEntityDto(ResponseStatus.SUCCESSFUL, message, responseDto);
    }

    @Override
    public ResponseEntityDto getLowStockProducts(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<ProductResponseDto> responseDto = productRepo.findLowStockProducts(pageable);
        String message = messageUtil.getMessage(CommonMessageConstant.COMMON_SUCCESS_LIST_RETRIEVED);
        return new ResponseEntityDto(ResponseStatus.SUCCESSFUL, message, responseDto);

    }

    @Override
    public ResponseEntityDto getProductCategoriesWithCounts() {
        List<ProductCategoryDto> productCategoryDtoList = productRepo.getProductCategoryInfo();
        String message = messageUtil.getMessage(CommonMessageConstant.COMMON_SUCCESS_LIST_RETRIEVED);
        return new ResponseEntityDto(ResponseStatus.SUCCESSFUL, message, productCategoryDtoList);
//        try {
//            List<ProductCategoryDto> productCategoryDtoList = productRepo.getProductCategoryInfo();
//            for (ProductCategoryDto productCategoryDto : productCategoryDtoList) {
//                System.out.println("Category: " + productCategoryDto.getCategoryName() +
//                        ", No. of Products: " + productCategoryDto.getNoProducts() +
//                        ", Total Value: " + productCategoryDto.getTotalValue());
//            }
//            return productCategoryDtoList;
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to get product category info " + e.getMessage() );
//        }
    }

}
