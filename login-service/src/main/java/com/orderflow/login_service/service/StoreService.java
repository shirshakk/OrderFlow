package com.orderflow.login_service.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.orderflow.login_service.model.Employee;
import com.orderflow.login_service.model.Role;
import com.orderflow.login_service.model.Status;
import com.orderflow.login_service.model.Store;
import com.orderflow.login_service.repository.EmployeeRepository;
import com.orderflow.login_service.repository.StoreRepository;

import jakarta.transaction.Transactional;

@Service
public class StoreService {
    
    @Autowired
    private StoreRepository storeRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public String createStore(String storeName, String email, String phone,
                               String firstName, String lastName, String password) {

        boolean isStoreExist = storeRepository.findByNameIgnoreCase(storeName).isPresent();
        if (isStoreExist) {
            return "Store already exists";
        }

        

        if (employeeRepository.existsByPhone(phone)) {
            return "Phone number already registered";
        }

        // ---- 1. save the store ----
        Store store = new Store();
        store.setName(storeName);
        store.setCode(generateStoreCode(storeName));
        store.setEmail(email);
        store.setPhone(phone);
        store.setStatus(Status.ACTIVE);
        store.setCreatedAt(Instant.now());
        store.setUpdatedAt(Instant.now());

        Store savedStore = storeRepository.save(store);

        // ---- 2. save the admin employee ----
        Employee admin = new Employee();
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setPhone(phone);
        admin.setStore(savedStore);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole(Role.ADMIN);
        admin.setStatus(Status.ACTIVE);
        admin.setCreatedAt(Instant.now());
        admin.setUpdatedAt(Instant.now());

        employeeRepository.save(admin);

        

        return "Store created successfully";
    }


     private String generateStoreCode(String storeName) {
        String prefix = storeName.replaceAll("[^A-Za-z]", "")
                .toUpperCase();

        if (prefix.length() < 3) {
            prefix = (prefix + "XXX").substring(0, 3);
        } else {
            prefix = prefix.substring(0, 3);
        }

        String candidate = prefix;
        int suffix = 1;

        while (storeRepository.existsByCode(candidate)) {
            candidate = prefix + suffix;
            suffix++;
        }

        return candidate;
    }
}
