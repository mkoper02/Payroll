package com.mkoper.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.Tax;
import java.util.Optional;


@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {
    
    Optional<Tax> findByName(String name);
}
