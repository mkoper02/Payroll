package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.PayrollRaportDto;
import com.mkoper.payroll.service.PayrollRaportService;

@RestController
@RequestMapping("api/")
public class PayrollRaportController {
    
    private PayrollRaportService payrollRaportService;

    public PayrollRaportController(PayrollRaportService payrollRaportService) {
        this.payrollRaportService = payrollRaportService;
    }

    @GetMapping("employee/{employeeId}/payrollraports")
    public List<PayrollRaportDto> getPayrollRaportsByEmployeeId(@PathVariable Long employeeId) {
        return payrollRaportService.getPayrollRaportsByEmployeeID(employeeId);
    }

    @GetMapping("employee/{employeeId}/payrollraports/{year}")
    public List<PayrollRaportDto> getPayrollRaportByYear(@PathVariable Integer year, @PathVariable Long employeeId) {
        return payrollRaportService.getPayrollRaportsByYear(year, employeeId); 
    }
}
