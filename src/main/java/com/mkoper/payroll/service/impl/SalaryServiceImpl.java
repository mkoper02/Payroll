package com.mkoper.payroll.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.dto.SalaryDto;
import com.mkoper.payroll.exceptions.EmployeeNotFoundException;
import com.mkoper.payroll.model.Salary;
import com.mkoper.payroll.repository.EmployeeRepository;
import com.mkoper.payroll.repository.EnrollmentRepository;
import com.mkoper.payroll.repository.SalaryRepository;
import com.mkoper.payroll.service.SalaryService;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired private SalaryRepository salaryRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private EnrollmentRepository enrollmentRepository;

    public SalaryServiceImpl(SalaryRepository salaryRepository, EmployeeRepository employeeRepository, EnrollmentRepository enrollmentRepository) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
        this.enrollmentRepository = enrollmentRepository;
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
    public SalaryDto saveSalary(SalaryDto salaryDto) {
        Salary salary = new Salary();

        salary.setId(salaryDto.getId());
        salary.setEmployee(employeeRepository.findById(salaryDto.getId()).get());
        salary.setGrossSalary(salaryDto.getGrossSalary());
        salary.setNetSalary(calculateNetSalary());

        // When creating employee we always create enrollment first and set Salary for null
        salary.setEnrollment(enrollmentRepository.findById(salaryDto.getId()).get());

        return mapToSalaryDto(salaryRepository.save(salary));
    }

    @Override
    public SalaryDto updateSalary(SalaryDto salaryDto, Long employeeId) {
        Salary salary = salaryRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Salary for employee with given ID could not be found!"));

        salary.setGrossSalary(salaryDto.getGrossSalary());

        return mapToSalaryDto(salaryRepository.save(salary));
    }

    public void deleteSalary(Long employeeId) {
        Salary salary = salaryRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Salary for employee with given ID could not be found"));
        salaryRepository.delete(salary);
    }
 
    private SalaryDto mapToSalaryDto(Salary salary) {
        SalaryDto salaryDto = new SalaryDto();

        salaryDto.setId(salary.getId());
        salaryDto.setGrossSalary(salary.getGrossSalary());
        salaryDto.setNetSalary(salary.getNetSalary());

        return salaryDto;
    }

    private Float calculateNetSalary() {
        // TODO: get tax and calculate cost
        return null;
    }
}
