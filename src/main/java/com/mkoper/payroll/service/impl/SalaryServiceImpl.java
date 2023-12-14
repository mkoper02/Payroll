package com.mkoper.payroll.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.mkoper.payroll.dto.SalaryDto;
import com.mkoper.payroll.exceptions.EmployeeNotFoundException;
import com.mkoper.payroll.model.Salary;
import com.mkoper.payroll.repository.SalaryRepository;
import com.mkoper.payroll.service.SalaryService;

public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    public SalaryServiceImpl(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @Override
    public List<SalaryDto> getAll() {
        List<Salary> salaries = salaryRepository.findAll();
        return salaries.stream().map((salary) -> mapToSalaryDto(salary)).collect(Collectors.toList());
    }

    @Override
    public SalaryDto getByEmployeeId(Long employeeId) {
        Salary salary = salaryRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Salary for employee with given ID could not be found!"));
        return mapToSalaryDto(salary);
    }

    @Override
    public SalaryDto updateSalary(SalaryDto salaryDto, Long employeeId) {
        Salary salary = salaryRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Salary for employee with given ID could not be found!"));

        salary.setGrossSalary(salaryDto.getGrossSalary());

        Salary updatedSalary = salaryRepository.save(salary);
        return mapToSalaryDto(updatedSalary);
    }
 
    private SalaryDto mapToSalaryDto(Salary salary) {
        SalaryDto salaryDto = new SalaryDto();

        salaryDto.setId(salary.getId());
        salaryDto.setGrossSalary(salary.getGrossSalary());
        salaryDto.setNetSalary(salary.getNetSalary());

        return salaryDto;
    }
}
