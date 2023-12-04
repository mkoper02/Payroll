package com.mkoper.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.Benefit;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {
    
}
