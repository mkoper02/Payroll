package com.mkoper.payroll.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mkoper.payroll.dto.PayrollRaportDto;
import com.mkoper.payroll.model.PayrollRaport;
import com.mkoper.payroll.repository.PayrollRaportRepository;
import com.mkoper.payroll.service.PayrollRaportService;

@Service
public class PayrollRaportServiceImpl implements PayrollRaportService {
    
    private PayrollRaportRepository payrollRaportRepository;

    public PayrollRaportServiceImpl(PayrollRaportRepository payrollRaportRepository){
        this.payrollRaportRepository = payrollRaportRepository;
    }

    @Override
    public List<PayrollRaportDto> getPayrollRaportByEmployeeID(Long employeeId) {
        List<PayrollRaport> payrollRaports = payrollRaportRepository.findByEmployeeId(employeeId);
        return payrollRaports.stream().map((payrollRaport) -> mapToPayrollRaportDto(payrollRaport)).collect(Collectors.toList());
    }

    @Override
    public List<PayrollRaportDto> getPayrollRaportByYear(Integer year, Long employeeId) {
        List<PayrollRaportDto> payrollRaports = getPayrollRaportByEmployeeID(employeeId);
        List<PayrollRaportDto> finalPayrollRaports = new ArrayList<>();

        for (int i = 0; i < payrollRaports.size(); i++) {
            if (payrollRaports.get(i).getDate().getYear() == year) 
                finalPayrollRaports.add(payrollRaports.get(i));
        }

        return finalPayrollRaports;
    }

    private PayrollRaportDto mapToPayrollRaportDto(PayrollRaport payrollRaport) {
        PayrollRaportDto payrollRaportDto = new PayrollRaportDto();

        payrollRaportDto.setId(payrollRaport.getId());
        payrollRaportDto.setDate(payrollRaport.getDate());
        payrollRaportDto.setBonus(payrollRaport.getBonus());
        payrollRaportDto.setTotalAmount(payrollRaport.getTotalAmount());

        return payrollRaportDto;
    }
}
