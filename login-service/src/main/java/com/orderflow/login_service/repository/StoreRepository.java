package com.orderflow.login_service.repository;

import com.orderflow.login_service.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByNameIgnoreCase(String name);
}
