package com.stockmanager.stockmanager_be.repo;

import com.stockmanager.stockmanager_be.dto.ProductCategoryDto;
import com.stockmanager.stockmanager_be.dto.response.ProductResponseDto;
import com.stockmanager.stockmanager_be.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ProductRepo extends JpaRepository<Product, Integer> {

//    @Query(value = "SELECT p.product_id, p.SKU, p.name, p.price, p.quantity, p.supplier, p.category_id, c.name, p.last_restocked "+
//    "FROM Product p "+
//            "JOIN Category c ON p.category_id = c.category_id "+
//    "WHERE p.category_id = :categoryId AND p.quantity BETWEEN 0 AND 10", nativeQuery = true)
@Query(value = """
        SELECT p.product_id, p.SKU, p.name, p.price, p.quantity, p.supplier, 
               p.category_id, c.name, p.last_restocked
        FROM Product p
        JOIN Category c ON p.category_id = c.category_id
        WHERE (:categoryId = -1 OR p.category_id = :categoryId)
          AND (
              :status = -1 OR
              (:status = 0 AND p.quantity = 0) OR
              (:status = 1 AND p.quantity > 0 AND p.quantity <= 10) OR
              (:status = 2 AND p.quantity > 10)
          ) ORDER BY product_id
        """, nativeQuery = true)
    Page<ProductResponseDto> findProductsFiltered(
            @Param("categoryId") Integer categoryId,
            @Param("status") Integer status,
            Pageable pageable
    );

    @Query(value = """
        SELECT p.product_id, p.SKU, p.name, p.price, p.quantity, p.supplier, 
               p.category_id, c.name, p.last_restocked
        FROM Product p
        JOIN Category c ON p.category_id = c.category_id
        WHERE p.quantity BETWEEN 0 AND 10
        ORDER BY p.product_id
        """, nativeQuery = true)
Page<ProductResponseDto> findLowStockProducts(Pageable pageable);

    @Query(value = """
    SELECT c.category_id,
           c.name AS categoryName,
           CAST(SUM(p.quantity) AS signed) AS noProducts,
           CAST(SUM(p.price * p.quantity) AS decimal(15,2)) AS totalValue
    FROM Category c
    LEFT JOIN Product p ON c.category_id = p.category_id
    GROUP BY c.category_id, c.name
""", nativeQuery = true)
    List<ProductCategoryDto> getProductCategoryInfo();


}
