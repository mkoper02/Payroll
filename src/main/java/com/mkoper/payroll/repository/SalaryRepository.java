package com.mkoper.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    
    // Check if salary with given employee ID exist in the db
    Boolean existsByEmployeeId(Long employeeId);
}
