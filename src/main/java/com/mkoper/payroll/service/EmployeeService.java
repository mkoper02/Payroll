package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.EmployeeDto;
import com.mkoper.payroll.model.Employee;

public interface EmployeeService {
	// Return all data in Employee class
	List<EmployeeDto> getAll();

	// Return employee data with given ID
	EmployeeDto getEmployeeById(Long employeeId);

	// Save new employee to the db
	Employee saveEmployee(Employee employee);

	// Update employee data
	EmployeeDto updateEmployee(EmployeeDto employeeDto, Long id);

	// Delete employee from the db
	void deleteEmployeeId(Long id);
}
