package com.stockmanager.stockmanager_be.dto;

import lombok.*;

import java.math.BigDecimal;


public interface ProductCategoryDto {

    Integer getCategoryId();
    String getCategoryName();
    Long getNoProducts();
    Double getTotalValue();

}
