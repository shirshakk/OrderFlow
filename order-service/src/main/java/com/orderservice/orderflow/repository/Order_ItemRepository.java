package com.orderservice.orderflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderservice.orderflow.model.Order_Item;

@Repository
public interface Order_ItemRepository extends JpaRepository<Order_Item, Long> {
    
}
