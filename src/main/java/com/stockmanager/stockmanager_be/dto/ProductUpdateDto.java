package com.stockmanager.stockmanager_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDto {
    private int productId;
    private String name;
    private String SKU;
    private double price;
    private int quantity;
    private String supplier;
    private Integer categoryId;
}
