package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.EmployeeDto;
import com.mkoper.payroll.model.Employee;

public interface EmployeeService {
	// Return all data in Employee class
	public List<EmployeeDto> getAll();

	// Return employee data with given ID
	public EmployeeDto getEmployeeById(Long employeeId);

	// Save new employee to the db
	public Employee saveEmployee(Employee employee);

	// Update employee data
	public EmployeeDto updateEmployee(EmployeeDto employeeDto, Long id);

	// Delete employee from the db
	public void deleteEmployeeId(Long id);
}
