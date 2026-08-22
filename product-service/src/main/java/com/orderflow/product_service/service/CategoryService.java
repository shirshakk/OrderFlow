package com.orderflow.product_service.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderflow.product_service.model.Status;
import com.orderflow.product_service.model.category;
import com.orderflow.product_service.repository.CategoryRepository;

import io.jsonwebtoken.Claims;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    public String createCategory(Claims claim,String categoryName,String description){
        if(categoryName.isEmpty()){
            return "Category name can not be empty";
        }
        Long storeId = claim.get("storeId", Long.TYPE);
        Long branchId = claim.get("branchId", Long.TYPE);
        if(storeId ==null){
            return "Store id can not be null";
        }else if(branchId ==null){
            return "Branch id can not be null";
        }

        category newCategory = new category();
        newCategory.setBranch_id(branchId);
        newCategory.setStore_id(storeId);
        newCategory.setName(categoryName);
        newCategory.setDescription(description);
        newCategory.setStatus(Status.Active);
        newCategory.setCreatedAt(Instant.now());
        newCategory.setUpdatedAt(Instant.now());
        categoryRepository.save(newCategory);

        return "New Category is created";
    }
}
