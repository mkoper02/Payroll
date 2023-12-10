package com.mkoper.payroll.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.PayrollRaport;

@Repository
public interface PayrollRaportRepository extends JpaRepository<PayrollRaport, Long> {

    List<PayrollRaport> findByEmployeeId(Long employeeId);

    List<PayrollRaport> findByDateBetweenAndEmployeeId(LocalDate start, LocalDate end, Long employeeId);
}
