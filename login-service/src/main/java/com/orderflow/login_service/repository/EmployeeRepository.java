package com.orderflow.login_service.repository;

import com.orderflow.login_service.model.Employee;
import com.orderflow.login_service.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByPhone(String phone);
    boolean existsByPhone(String phone);

    // used to match PIN within a branch (PIN is hashed, so we compare in the service layer)
    List<Employee> findAllByBranchIdAndStatus(Long branchId, Status status);

    List<Employee> findAllByStoreIdAndStatus(Long storeId, Status status);

    Employee findByStoreIdAndFirstName(Long storeId, String firstName);
}
