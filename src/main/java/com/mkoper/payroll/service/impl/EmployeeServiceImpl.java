package com.mkoper.payroll.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.dto.EmployeeDto;
import com.mkoper.payroll.exceptions.EmployeeNotFoundException;
import com.mkoper.payroll.exceptions.PositionNotFoundException;
import com.mkoper.payroll.model.Employee;
import com.mkoper.payroll.repository.EmployeeRepository;
import com.mkoper.payroll.repository.PositionRepository;
import com.mkoper.payroll.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private PositionRepository positionRepository;
    
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    public List<EmployeeDto> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee) -> mapToEmployeeDto(employee)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee could not be found!"));
        return mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getEmployeesByPositionId(Long positionId) {
        
        List<Employee> employees = employeeRepository.findByJobPosition(positionRepository.findById(positionId).orElseThrow(() -> new PositionNotFoundException("Position could not be found!")));
        return employees.stream().map((employee) -> mapToEmployeeDto(employee)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        if (employeeDto.getId() == null) {
            throw new IllegalArgumentException("ID was not given!");
        }

        Employee employee = employeeRepository.findById(employeeDto.getId()).orElseThrow(() -> new EmployeeNotFoundException("Employee could not be found!"));

        if(employeeDto.getDateOfBirth() != null) employee.setDateOfBirth(employeeDto.getDateOfBirth());
        if(employeeDto.getFirstName() != null) employee.setFirstName(employeeDto.getFirstName());
        if(employeeDto.getLastName() != null) employee.setLastName(employeeDto.getLastName());
        if(employeeDto.getEmail() != null) employee.setEmail(employeeDto.getEmail());
        if(employeeDto.getPhoneNumber() != null) employee.setPhoneNumber(employeeDto.getPhoneNumber());

        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToEmployeeDto(updatedEmployee);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        if (employee.getJoinDate() == null) employee.setJoinDate(LocalDate.now());
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeeId(Long id) {
        employeeRepository.delete(employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee could not be found!")));
    }

    private EmployeeDto mapToEmployeeDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setDateOfBirth(employee.getDateOfBirth());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setPhoneNumber(employee.getPhoneNumber());
        employeeDto.setJobPosition(employee.getJobPosition().getName());

        return employeeDto;
    }
}
