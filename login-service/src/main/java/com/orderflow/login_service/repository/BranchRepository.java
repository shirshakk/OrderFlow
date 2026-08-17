package com.orderflow.login_service.repository;

import com.orderflow.login_service.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByCodeAndStoreId(String code, Long storeId);
}
