package com.mkoper.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.PayrollRaport;

@Repository
public interface PayrollRaportRepository extends JpaRepository<PayrollRaport, Long> {
    
}
