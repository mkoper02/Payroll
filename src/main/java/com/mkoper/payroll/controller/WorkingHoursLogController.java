package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.WorkingHoursLogDto;
import com.mkoper.payroll.service.WorkingHoursLogService;

@RestController
public class WorkingHoursLogController {
    
    @Autowired
    private WorkingHoursLogService workingHoursLogService;

    public WorkingHoursLogController(WorkingHoursLogService workingHoursLogService) {
        this.workingHoursLogService = workingHoursLogService;
    }

    @GetMapping("employee/{employeeId}/workinglogs")
    public List<WorkingHoursLogDto> getWorkingLogsByEmployeeId(@PathVariable Long employeeId) {
        return workingHoursLogService.getWorkingLogsByEmployeeId(employeeId);
    }

    @GetMapping("employee/{employeeId}/workinglogs/{year}")
    public List<WorkingHoursLogDto> getWorkingLogsByYear(@PathVariable Long employeeId, @PathVariable Integer year) {
        return workingHoursLogService.getWorkingLogByYear(employeeId, year);
    }
}
