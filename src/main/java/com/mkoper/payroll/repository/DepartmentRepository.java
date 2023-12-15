package com.mkoper.payroll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    Optional<Department> findByName(String departmentName);
}
