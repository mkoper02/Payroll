package com.mkoper.payroll.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.WorkingHoursLog;

@Repository
public interface WorkingHoursLogRepository extends JpaRepository<WorkingHoursLog, Long>  {
    
    List<WorkingHoursLog> findByEmployeeId(Long employeeId);

    List<WorkingHoursLog> findByDateBetweenAndEmployeeId(LocalDate start, LocalDate end, Long employeeId);

    List<WorkingHoursLog> findByDateBetween(LocalDate start, LocalDate end);
}
