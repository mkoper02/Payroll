package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mkoper.payroll.dto.SalaryDto;
import com.mkoper.payroll.service.SalaryService;

@RequestMapping
public class SalaryController {
    
    @Autowired
    private SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("employee/salary/{employeeId}")
    public ResponseEntity<SalaryDto> getSalaryByEmployeeId(Long employeeId) {
        return ResponseEntity.ok(salaryService.getByEmployeeId(employeeId));
    }

    @GetMapping("employee/salary")
    public List<SalaryDto> getSalaries() {
        return salaryService.getAll();
    }

    @PutMapping("employee/salary/{employeeId}/update")
    public ResponseEntity<SalaryDto> updateSalary(@RequestBody SalaryDto salaryDto, @PathVariable Long employeeId) {
        return new ResponseEntity<>(salaryService.updateSalary(salaryDto, employeeId), HttpStatus.OK);
    }
}