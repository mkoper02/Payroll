package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.EmployeeDto;
import com.mkoper.payroll.model.Employee;
import com.mkoper.payroll.service.EmployeeService;

@RestController
@RequestMapping(path = "api/")
public class EmployeeController {
    
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// get all employees in the db
	@GetMapping("employee")
	public List<EmployeeDto> getEmployees() {
		return employeeService.getAll();
	}
	
	// get employee with given id
	@GetMapping("employee/{employeeId}")
	public ResponseEntity<EmployeeDto> getEmployeeId(@PathVariable Long employeeId) {
		return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
	}

	// add employee to the db
	@PostMapping("employee/create")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
	}

	// update employee data in the db
	@PutMapping("employee/{employeeId}/update")
	public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable("employeeId") Long employeeId) {
		return new ResponseEntity<>(employeeService.updateEmployee(employeeDto, employeeId), HttpStatus.OK);
	}

	// delete employee data from the db
	@DeleteMapping("employee/{employeeId}/delete")
	public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") Long employeeId) {
		employeeService.deleteEmployeeId(employeeId);
		return new ResponseEntity<>("Employee deleted", HttpStatus.OK);
	}
}