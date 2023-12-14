package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.PayrollRaportDto;
import com.mkoper.payroll.service.PayrollRaportService;

@RestController
public class PayrollRaportController {
    
    @Autowired 
    private PayrollRaportService payrollRaportService;

    public PayrollRaportController(PayrollRaportService payrollRaportService) {
        this.payrollRaportService = payrollRaportService;
    }

    @GetMapping("employee/{employeeId}/payrollraports")
    public List<PayrollRaportDto> getPayrollRaportsByEmployeeId(@PathVariable Long employeeId) {
        return payrollRaportService.getPayrollRaportsByEmployeeId(employeeId);
    }

    @GetMapping("employee/{employeeId}/payrollraports/{year}")
    public List<PayrollRaportDto> getPayrollRaportByYear(@PathVariable Integer year, @PathVariable Long employeeId) {
        return payrollRaportService.getPayrollRaportsByYear(year, employeeId); 
    }

    @PutMapping("payrollraport/create/allemployees")
    public ResponseEntity<PayrollRaportDto> addPayrollRaport() {
        // TODO: give signal to create payroll raports for all employees for given month and year
        return null;
    }
}
