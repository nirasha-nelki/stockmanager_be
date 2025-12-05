package com.stockmanager.stockmanager_be.api;

import com.stockmanager.stockmanager_be.constant.ApiUriConstants;
import com.stockmanager.stockmanager_be.dto.*;
import com.stockmanager.stockmanager_be.dto.request.ProductCreateDto;
import com.stockmanager.stockmanager_be.dto.response.ProductResponseDto;
import com.stockmanager.stockmanager_be.dto.response.ResponseEntityDto;
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
    public ResponseEntity<ResponseEntityDto> addProduct(@RequestBody ProductCreateDto productCreateDto) {
        ResponseEntityDto responseEntityDto = productServiceImpl.createProduct(productCreateDto);
        return new ResponseEntity<>(responseEntityDto, HttpStatus.CREATED);
    }

    @PostMapping(value = ApiUriConstants.PRODUCT_LIST_ADD, consumes = "application/json")
    public ResponseEntity<ResponseEntityDto> addProductList(@RequestBody List<ProductBulkDto> productBulkDtoList) {
       ResponseEntityDto responseEntityDto = productServiceImpl.saveProductList(productBulkDtoList);
        return new ResponseEntity<>(responseEntityDto, HttpStatus.CREATED);
    }

    @GetMapping(ApiUriConstants.PRODUCT_GET)
    public ResponseEntity<ResponseEntityDto> getProduct(@PathVariable int productId) {
        ResponseEntityDto responseEntityDto = productServiceImpl.getProductById(productId);
        return new ResponseEntity<>(responseEntityDto, HttpStatus.OK);
    }

    @GetMapping(ApiUriConstants.PRODUCTS_GET_ALL)
    public ResponseEntity<ResponseEntityDto> getAllProducts(){
        ResponseEntityDto responseEntityDto = productServiceImpl.getAllProducts();
        return new ResponseEntity<>(responseEntityDto, HttpStatus.OK);
    }

    @GetMapping(ApiUriConstants.PRODUCT_LOW_STOCK)
    public ResponseEntity<ResponseEntityDto> getLowStockProducts(@RequestParam int page, @RequestParam int size) {
        ResponseEntityDto responseEntityDto = productServiceImpl.getLowStockProducts(page, size);
        return new ResponseEntity<>(responseEntityDto, HttpStatus.OK);
    }

    @GetMapping(ApiUriConstants.PRODUCTS_CATEGORY)
    public ResponseEntity<ResponseEntityDto> getProductCategoryInfo(){
        ResponseEntityDto responseEntityDto = productServiceImpl.getProductCategoriesWithCounts();
        return new ResponseEntity<>(responseEntityDto, HttpStatus.OK);
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
    public ResponseEntity<ResponseEntityDto> paginatedProducts(@RequestParam int page, @RequestParam int size, @RequestParam int categoryId, @RequestParam int status) {
        ResponseEntityDto responseEntityDto = productServiceImpl.getProductsPaginated(page, size, categoryId, status);
        return new ResponseEntity<>(responseEntityDto, HttpStatus.OK);
    }

    @PutMapping(ApiUriConstants.PRODUCT_UPDATE)
    public ResponseEntity<ResponseEntityDto> updateProduct(@PathVariable int productId, @RequestBody ProductUpdateDto productUpdateDto) {
        ResponseEntityDto responseEntityDto = productServiceImpl.updateProduct(productUpdateDto);
        return new ResponseEntity<>(responseEntityDto, HttpStatus.OK);
    }

    @DeleteMapping(ApiUriConstants.PRODUCT_DELETE)
    public ResponseEntity<ResponseEntityDto> deleteProduct(@PathVariable int productId){
        ResponseEntityDto responseEntityDto = productServiceImpl.deleteProduct(productId);
        return new ResponseEntity<>(responseEntityDto, HttpStatus.OK);
    }

}
