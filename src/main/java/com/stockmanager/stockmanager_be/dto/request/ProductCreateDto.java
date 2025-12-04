package com.stockmanager.stockmanager_be.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {

    private String name;
    private String SKU;
    private double price;
    private int quantity;
    private String supplier;
    private Integer categoryId;


}
