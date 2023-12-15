package com.mkoper.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.Department;
import com.mkoper.payroll.model.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    
    List<Position> findByDepartment(Department department);
}
