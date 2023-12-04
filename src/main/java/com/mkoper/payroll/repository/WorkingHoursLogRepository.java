package com.mkoper.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.WorkingHoursLog;

@Repository
public interface WorkingHoursLogRepository extends JpaRepository<WorkingHoursLog, Long>  {
    
}
