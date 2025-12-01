package com.stockmanager.stockmanager_be.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;
    private String name;

    @Column(name = "sku", unique = true, nullable = false)
    private String SKU;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private Double price;
    private Integer quantity;
    private String supplier;

    @Column(name = "last_restocked")
    private Timestamp lastRestocked;

    @PrePersist
    protected void onCreate() {
        this.lastRestocked = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastRestocked = new Timestamp(System.currentTimeMillis());
    }



}
