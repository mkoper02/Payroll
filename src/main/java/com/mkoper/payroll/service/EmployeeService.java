package com.mkoper.payroll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.model.Employee;
import com.mkoper.payroll.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private final EmployeeRepository employeeRepository;
	
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	// Return all data in Employee class
	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}

	// Return employee data with given ID
	public Employee getEmployeeId(Long id) {
		return employeeRepository.findById((long)id);
	}

	// Return list of employees with given first name
	public List<Employee> getEmployeeFirstName(String firstName) {
		return employeeRepository.findByFirstName(firstName);
	}

	// Save new employee to the db
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
}
