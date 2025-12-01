package com.stockmanager.stockmanager_be.repo;

import com.stockmanager.stockmanager_be.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
