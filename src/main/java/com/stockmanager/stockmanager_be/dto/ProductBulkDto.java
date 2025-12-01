package com.stockmanager.stockmanager_be.dto;

import lombok.Data;

@Data
public class ProductBulkDto {

    private String name;
    private String SKU;
    private double price;
    private int quantity;
    private String supplier;
    private String categoryName;
}
