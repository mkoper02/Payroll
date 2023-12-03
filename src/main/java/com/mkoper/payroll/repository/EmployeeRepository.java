package com.mkoper.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // Optional<Employee> findById(Long id);
    // Search for employee with given ID
    Employee findById(long id);

    // Search for employees with given name
    List<Employee> findByFirstName(String firstName);

}