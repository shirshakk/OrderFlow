package com.orderflow.login_service.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderflow.login_service.model.Branch;
import com.orderflow.login_service.model.Status;
import com.orderflow.login_service.model.Store;
import com.orderflow.login_service.repository.BranchRepository;

import jakarta.transaction.Transactional;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;
    
    @Transactional
    public String CreateBranch(Store store,String password,String address,
        String city,String state,String pincode,String phone,String country,Long storeId
    ){
        boolean isPhonenoExist=branchRepository.findByStoreIdAndPhone(storeId, phone);
        if(isPhonenoExist){
             return "This No. is already associated with the branch";
        }
        String branchCode=generateBranchCode(store.getCode());
        Branch branch = new Branch();
        branch.setStore(store);
        branch.setAddress(address);
        branch.setCity(city);
        branch.setStatus(Status.ACTIVE);
        branch.setCountry(country);
        branch.setCode(branchCode);
        branch.setPhone(phone);
        branch.setPassword(password);
        branch.setState(state);
        branch.setPincode(pincode);
        branch.setCreatedAt(Instant.now());
        branch.setUpdatedAt(Instant.now());
        
        branchRepository.save(branch);
        return "Branch created successfully";
        
    }
    private String generateBranchCode(String storeCode) {


        int suffix = 1;

        String candidate = storeCode + String.format("%03d", suffix);

        while (branchRepository.existsByCode(candidate)) {
            suffix++;
            candidate = storeCode + String.format("%03d", suffix);
        }

        return candidate;
    }
}
