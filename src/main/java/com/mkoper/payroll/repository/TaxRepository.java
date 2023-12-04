package com.mkoper.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.Tax;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {
    
}
