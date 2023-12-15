package com.mkoper.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.Employee;
import com.mkoper.payroll.model.Position;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // find employees with given position ID
    List<Employee> findByJobPosition(Position position);
}