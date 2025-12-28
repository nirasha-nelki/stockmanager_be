package com.stockmanager.stockmanager_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatisticsDto {

    private  int totalProducts;
    private  int outOfStockProducts;
    private  int lowStockProducts;
    private  double totalInventoryValue;

}
