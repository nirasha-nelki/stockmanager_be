package com.stockmanager.stockmanager_be.api;

import com.stockmanager.stockmanager_be.constant.ApiUriConstants;
import com.stockmanager.stockmanager_be.dto.*;
import com.stockmanager.stockmanager_be.dto.request.ProductCreateDto;
import com.stockmanager.stockmanager_be.dto.response.ProductResponseDto;
import com.stockmanager.stockmanager_be.service.impl.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @PostMapping(value = ApiUriConstants.PRODUCT_ADD, consumes = "application/json")
    public ResponseEntity<?> addProduct(@RequestBody ProductCreateDto productCreateDto) {


        try {
            ProductResponseDto productResponseDto = productServiceImpl.createProduct(productCreateDto);
            return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = ApiUriConstants.PRODUCT_LIST_ADD, consumes = "application/json")
    public ResponseEntity<?> addProductList(@RequestBody List<ProductBulkDto> productBulkDtoList) {

        try {
            int result = productServiceImpl.saveProductList(productBulkDtoList);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(ApiUriConstants.PRODUCT_GET)
    public ResponseEntity<?> getProduct(@PathVariable int productId) {
        try{
            ProductResponseDto productResponseDto = productServiceImpl.getProductById(productId);
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(ApiUriConstants.PRODUCTS_GET_ALL)
    public ResponseEntity<?> getAllProducts(){

        try {
            List<ProductResponseDto> productResponseDtoList = productServiceImpl.getAllProducts();
            return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(ApiUriConstants.PRODUCT_LOW_STOCK)
    public ResponseEntity<?> getLowStockProducts(@RequestParam int page, @RequestParam int size) {
        try {
            Page<ProductResponseDto> productResponseDtoList = productServiceImpl.getLowStockProducts(page, size);
            return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(ApiUriConstants.PRODUCTS_CATEGORY)
    public ResponseEntity<?> getProductCategoryInfo(){
        try{
            List<ProductCategoryDto> productCategoryDtoList = productServiceImpl.getProductCategoriesWithCounts();
            return new ResponseEntity<>(productCategoryDtoList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//
//    @GetMapping("/paginated")
//    public ResponseEntity<?> paginatedProducts(@RequestParam int page, @RequestParam int size) {
//        try {
//            Page<ProductResponseDto> productResponseDtoList = productServiceImpl.getProductsPaginated(page, size);
//            return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @GetMapping(ApiUriConstants.PRODUCTS_PAGINATED)
    public ResponseEntity<?> paginatedProducts(@RequestParam int page, @RequestParam int size, @RequestParam int categoryId, @RequestParam int status) {
        try {
            Page<ProductResponseDto> productResponseDtoList = productServiceImpl.getProductsPaginated(page, size, categoryId, status);
            return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(ApiUriConstants.PRODUCT_UPDATE)
    public ResponseEntity<?> updateProduct(@PathVariable int productId, @RequestBody ProductUpdateDto productUpdateDto) {
        try {

            ProductResponseDto productResponseDto = productServiceImpl.updateProduct(productUpdateDto);
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(ApiUriConstants.PRODUCT_DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable int productId){
        try{
            int results = productServiceImpl.deleteProduct(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
