package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.DateDto;
import com.mkoper.payroll.dto.PayrollRaportDto;

public interface PayrollRaportService {
    // get list of all payroll raports for employee with given ID
    public List<PayrollRaportDto> getPayrollRaportsByEmployeeId(Long employeeId);

    // get list of payroll raports for employee with given ID for given year
    public List<PayrollRaportDto> getPayrollRaportsByYear(Integer year, Long employeeId);

    // save new payroll raport for employee with given ID
    public PayrollRaportDto savePayrollRaportForEmployee(PayrollRaportDto payrollRaportDto);

    // create and save new payroll raporst for all employees for given date
    public List<PayrollRaportDto> savePayrollRaportsForAll(DateDto date);

    // update payroll raport
    public PayrollRaportDto updatePayrollRaport(PayrollRaportDto payrollRaportDto);
}
