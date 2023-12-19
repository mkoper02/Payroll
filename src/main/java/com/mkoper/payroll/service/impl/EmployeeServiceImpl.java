package com.mkoper.payroll.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.dto.EmployeeDto;
import com.mkoper.payroll.dto.PositionDto;
import com.mkoper.payroll.exceptions.InvalidDataGivenException;
import com.mkoper.payroll.exceptions.ObjectNotFoundException;
import com.mkoper.payroll.model.Employee;
import com.mkoper.payroll.model.Position;
import com.mkoper.payroll.repository.EmployeeRepository;
import com.mkoper.payroll.repository.PositionRepository;
import com.mkoper.payroll.service.EmployeeService;
import com.mkoper.payroll.service.PayrollRaportService;
import com.mkoper.payroll.service.UserService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private PositionRepository positionRepository;

    @Autowired private PayrollRaportService payrollRaportService;
    @Autowired private UserService userService;
    
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository, PayrollRaportService payrollRaportService, UserService userService) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.payrollRaportService = payrollRaportService;
        this.userService = userService;
    }

    @Override
    public List<EmployeeDto> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        System.out.println(employees);

        return employees.stream().map((employee) -> mapToEmployeeDto(employee)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        return mapToEmployeeDto(employeeRepository.findById(employeeId).orElseThrow(() -> new ObjectNotFoundException("Employee could not be found!")));
    }

    @Override
    public List<EmployeeDto> getEmployeesByPositionId(Long positionId) {
        
        List<Employee> employees = employeeRepository.findByJobPosition(positionRepository.findById(positionId).orElseThrow(() -> new ObjectNotFoundException("Position could not be found!")));
        return employees.stream().map((employee) -> mapToEmployeeDto(employee)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        if (employeeDto.getId() == null) {
            throw new InvalidDataGivenException("ID was not given!");
        }

        Employee employee = employeeRepository.findById(employeeDto.getId()).orElseThrow(() -> new ObjectNotFoundException("Employee could not be found!"));

        if(employeeDto.getDateOfBirth() != null) employee.setDateOfBirth(employeeDto.getDateOfBirth());
        if(employeeDto.getFirstName() != null) employee.setFirstName(employeeDto.getFirstName());
        if(employeeDto.getLastName() != null) employee.setLastName(employeeDto.getLastName());
        if(employeeDto.getEmail() != null) employee.setEmail(employeeDto.getEmail());
        if(employeeDto.getPhoneNumber() != null) employee.setPhoneNumber(employeeDto.getPhoneNumber());
        if(employeeDto.getJobPosition() != null) {
            employee.setJobPosition(positionRepository.findByName(employeeDto.getJobPosition().getName()).orElseThrow(() -> new ObjectNotFoundException("Position could not be found!")));
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToEmployeeDto(updatedEmployee);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        if (employee.getJobPosition() == null) 
            throw new InvalidDataGivenException("Job position was not given!");
        if (employee.getJoinDate() == null) employee.setJoinDate(LocalDate.now());
        
        employee.setJobPosition(positionRepository.findByName(employee.getJobPosition().getName()).orElseThrow(() -> new ObjectNotFoundException("Position could not be found!")));
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeeId(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Employee could not be found!"));

        // delete all payroll raports and user
        payrollRaportService.deleteByEmployeeId(id);
        userService.deleteUserId(id);

        employeeRepository.delete(employee);
    }

    private EmployeeDto mapToEmployeeDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setDateOfBirth(employee.getDateOfBirth());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setPhoneNumber(employee.getPhoneNumber());
        employeeDto.setJoinDate(employee.getJoinDate());

        Position position = positionRepository.findByName(employee.getJobPosition().getName()).orElseThrow(() -> new ObjectNotFoundException("Position could not be found!"));
        employeeDto.setJobPosition(mapToPosiotionDto(position));

        return employeeDto;
    }

    private PositionDto mapToPosiotionDto(Position position) {
        PositionDto positionDto = new PositionDto();

        positionDto.setId(position.getId());
        positionDto.setName(position.getName());
        positionDto.setDepartmentName(position.getDepartment().getName());

        return positionDto;
    }
}
