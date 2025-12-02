package com.stockmanager.stockmanager_be.service;

import com.stockmanager.stockmanager_be.dto.ProductBulkDto;
import com.stockmanager.stockmanager_be.dto.ProductCreateDto;
import com.stockmanager.stockmanager_be.dto.ProductResponseDto;
import com.stockmanager.stockmanager_be.dto.ProductUpdateDto;
import com.stockmanager.stockmanager_be.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductCreateDto productCreateDto);
    ProductResponseDto updateProduct(ProductUpdateDto productUpdateDto);
    ProductResponseDto getProductById(int productId);
    List<ProductResponseDto> getAllProducts();
    int deleteProduct(int productId);
    int saveProductList(List<ProductBulkDto> productBulkDtoList);
    Page<ProductResponseDto> getProductsPaginated(int page, int size, int categoryId, int status);
    Page<ProductResponseDto> getLowStockProducts(int page, int size);
//    List<ProductResponseDto> getRecentlyAddedProducts(int count);
}
