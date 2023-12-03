package com.mkoper.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkoper.payroll.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{

    
}