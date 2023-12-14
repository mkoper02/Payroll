package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    // get salary of employee with given ID
    @GetMapping("employee/{employeeId}/salary")
    public ResponseEntity<SalaryDto> getSalaryByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(salaryService.getByEmployeeId(employeeId));
    }

    // get all salaries
    @GetMapping("salary")
    public List<SalaryDto> getSalaries() {
        return salaryService.getAll();
    }

    // create salary for employee with given ID
    @PostMapping("salary/create")
    public ResponseEntity<SalaryDto> addSalary(@RequestBody SalaryDto salaryDto) {
        return new ResponseEntity<>(salaryService.saveSalary(salaryDto), HttpStatus.CREATED);
    }

    // upadte gross salary of employee with given ID
    @PutMapping("salary/update/{employeeId}")
    public ResponseEntity<SalaryDto> updateSalary(@RequestBody SalaryDto salaryDto, @PathVariable Long employeeId) {
        return new ResponseEntity<>(salaryService.updateSalary(salaryDto, employeeId), HttpStatus.OK);
    }
}