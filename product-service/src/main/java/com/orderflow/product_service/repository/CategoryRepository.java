package com.orderflow.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderflow.product_service.model.category;

public interface CategoryRepository extends JpaRepository<category,Long> {
    
}
