package com.mkoper.payroll.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.dto.PayrollRaportDto;
import com.mkoper.payroll.model.PayrollRaport;
import com.mkoper.payroll.repository.PayrollRaportRepository;
import com.mkoper.payroll.service.PayrollRaportService;

@Service
public class PayrollRaportServiceImpl implements PayrollRaportService {
    
    @Autowired 
    private PayrollRaportRepository payrollRaportRepository;

    public PayrollRaportServiceImpl(PayrollRaportRepository payrollRaportRepository){
        this.payrollRaportRepository = payrollRaportRepository;
    }

    @Override
    public List<PayrollRaportDto> getPayrollRaportsByEmployeeId(Long employeeId) {
        List<PayrollRaport> payrollRaports = payrollRaportRepository.findByEmployeeId(employeeId);
        return payrollRaports.stream().map((payrollRaport) -> mapToPayrollRaportDto(payrollRaport)).collect(Collectors.toList());
    }

    @Override
    public List<PayrollRaportDto> getPayrollRaportsByYear(Integer year, Long employeeId) {
        List<PayrollRaport> payrollRaportsBetweenDates = payrollRaportRepository.findByDateBetweenAndEmployeeId(LocalDate.of(year, 01, 01), LocalDate.of(year, 12, 31), employeeId);
        return payrollRaportsBetweenDates.stream().map((payrollRaport) -> mapToPayrollRaportDto(payrollRaport)).collect(Collectors.toList());
    }

    private PayrollRaportDto mapToPayrollRaportDto(PayrollRaport payrollRaport) {
        PayrollRaportDto payrollRaportDto = new PayrollRaportDto();

        payrollRaportDto.setId(payrollRaport.getId());
        payrollRaportDto.setDate(payrollRaport.getDate());
        payrollRaportDto.setBonus(payrollRaport.getBonus());
        payrollRaportDto.setNetSalary(payrollRaport.getNetSalary());

        return payrollRaportDto;
    }
}
