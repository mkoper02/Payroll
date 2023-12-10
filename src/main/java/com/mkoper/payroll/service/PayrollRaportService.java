package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.PayrollRaportDto;

public interface PayrollRaportService {
    // get list of all payroll raports for employee with given ID
    List<PayrollRaportDto> getPayrollRaportsByEmployeeId(Long employeeId);

    // get list of payroll raports for employee with given ID for given year
    List<PayrollRaportDto> getPayrollRaportsByYear(Integer year, Long employeeId);
}
