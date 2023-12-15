package com.mkoper.payroll.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.dto.SalaryDto;
import com.mkoper.payroll.exceptions.EmployeeNotFoundException;
import com.mkoper.payroll.model.Salary;
import com.mkoper.payroll.repository.EmployeeRepository;
import com.mkoper.payroll.repository.SalaryRepository;
import com.mkoper.payroll.service.SalaryService;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired private SalaryRepository salaryRepository;
    @Autowired private EmployeeRepository employeeRepository;

    public SalaryServiceImpl(SalaryRepository salaryRepository, EmployeeRepository employeeRepository) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<SalaryDto> getAll() {
        List<Salary> salaries = salaryRepository.findAll();
        return salaries.stream().map((salary) -> mapToSalaryDto(salary)).collect(Collectors.toList());
    }

    @Override
    public SalaryDto getByEmployeeId(Long employeeId) {
        Salary salary = salaryRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee could not be found!"));
        return mapToSalaryDto(salary);
    }

    @Override
    public SalaryDto updateSalary(SalaryDto salaryDto, Long employeeId) {
        Salary salary = salaryRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee could not be found!"));

        if (salaryDto.getGrossSalary() != null) salary.setGrossSalary(salaryDto.getGrossSalary());
        if (salaryDto.getHours() != null) salary.setHours(salaryDto.getHours());
        if (salaryDto.getContractType() != null && validContractType(salaryDto.getContractType())) salary.setContractType(salaryDto.getContractType());

        return mapToSalaryDto(salaryRepository.save(salary));
    }

    @Override
    public Salary saveSalary(Salary salary) {
        if (validContractType(salary.getContractType())) {
            salary.setEmployee(employeeRepository.findById(salary.getId()).orElseThrow(() -> new EmployeeNotFoundException("Employee could not be found!")));
            return salaryRepository.save(salary);
        }
        throw new IllegalArgumentException("Invalid contract type");
    }

    @Override
    public void deleteSalary(Long employeeId) {
        Salary salary = salaryRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee could not be found!"));
        salaryRepository.delete(salary);
    }

    private SalaryDto mapToSalaryDto(Salary salary) {
        SalaryDto salaryDto = new SalaryDto();

        salaryDto.setId(salary.getId());
        salaryDto.setContractType(salary.getContractType());
        salaryDto.setGrossSalary(salary.getGrossSalary());
        salaryDto.setHours(salary.getHours());

        return salaryDto;
    }

    private Boolean validContractType(String contractType) {
        if (contractType.equals("Umowa o prace") || contractType.equals("Umowa-zlecenie")) 
            return true;
        return false;
    }
    
}
