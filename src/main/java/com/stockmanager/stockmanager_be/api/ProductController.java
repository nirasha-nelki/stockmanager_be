package com.stockmanager.stockmanager_be.api;

import com.stockmanager.stockmanager_be.dto.ProductBulkDto;
import com.stockmanager.stockmanager_be.dto.ProductCreateDto;
import com.stockmanager.stockmanager_be.dto.ProductResponseDto;
import com.stockmanager.stockmanager_be.dto.ProductUpdateDto;
import com.stockmanager.stockmanager_be.entity.Product;
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

    @PostMapping(value = "/add_product", consumes = "application/json")
    public ResponseEntity<?> addProduct(@RequestBody ProductCreateDto productCreateDto) {


        try {
            ProductResponseDto productResponseDto = productServiceImpl.createProduct(productCreateDto);
            return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/add_product_list", consumes = "application/json")
    public ResponseEntity<?> addProductList(@RequestBody List<ProductBulkDto> productBulkDtoList) {

        try {
            int result = productServiceImpl.saveProductList(productBulkDtoList);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable int productId) {
        try{
            ProductResponseDto productResponseDto = productServiceImpl.getProductById(productId);
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get_all")
    public ResponseEntity<?> getAllProducts(){

        try {
            List<ProductResponseDto> productResponseDtoList = productServiceImpl.getAllProducts();
            return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/low_stock")
    public ResponseEntity<?> getLowStockProducts(@RequestParam int page, @RequestParam int size) {
        try {
            Page<ProductResponseDto> productResponseDtoList = productServiceImpl.getLowStockProducts(page, size);
            return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
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


    @GetMapping("/paginated")
    public ResponseEntity<?> paginatedProducts(@RequestParam int page, @RequestParam int size, @RequestParam int categoryId, @RequestParam int status) {
        try {
            Page<ProductResponseDto> productResponseDtoList = productServiceImpl.getProductsPaginated(page, size, categoryId, status);
            return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update_product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable int productId, @RequestBody ProductUpdateDto productUpdateDto) {
        try {

            ProductResponseDto productResponseDto = productServiceImpl.updateProduct(productUpdateDto);
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete_product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId){
        try{
            int results = productServiceImpl.deleteProduct(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
