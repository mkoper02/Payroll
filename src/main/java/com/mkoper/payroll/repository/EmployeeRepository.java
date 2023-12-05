package com.mkoper.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}