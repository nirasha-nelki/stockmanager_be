package com.stockmanager.stockmanager_be.service;

import com.stockmanager.stockmanager_be.dto.*;
import com.stockmanager.stockmanager_be.dto.request.ProductCreateDto;
import com.stockmanager.stockmanager_be.dto.response.ProductResponseDto;
import com.stockmanager.stockmanager_be.dto.response.ResponseEntityDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ResponseEntityDto createProduct(ProductCreateDto productCreateDto);
    ResponseEntityDto updateProduct(ProductUpdateDto productUpdateDto);
    ResponseEntityDto getProductById(int productId);
    ResponseEntityDto getAllProducts();
    ResponseEntityDto deleteProduct(int productId);
    ResponseEntityDto saveProductList(List<ProductBulkDto> productBulkDtoList);
    ResponseEntityDto getProductsPaginated(int page, int size, int categoryId, int status);
    ResponseEntityDto getLowStockProducts(int page, int size);
//    List<ProductResponseDto> getRecentlyAddedProducts(int count);
    ResponseEntityDto getProductCategoriesWithCounts();
    ResponseEntityDto getProductStatistics();
}
