package com.stockmanager.stockmanager_be.repo;

import com.stockmanager.stockmanager_be.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
