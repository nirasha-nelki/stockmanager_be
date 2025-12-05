package com.stockmanager.stockmanager_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private int productId;
    private String SKU;
    private String name;
    private double price;
    private int quantity;
    private String supplier;
    private int categoryId;
    private String categoryName;
    private LocalDateTime lastRestocked;
}
