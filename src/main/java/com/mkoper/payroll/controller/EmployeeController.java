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
		return employeeService.findAll();
	}
	// get employee with given id
	@GetMapping("employee/{id}")
	public ResponseEntity<EmployeeDto> getEmployeeId(@PathVariable Long id) {
		return ResponseEntity.ok(employeeService.findEmployeeById(id));
	}

	// add employee to the db
	@PostMapping("employee/create")
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}

	// update employee data in the db
	@PutMapping("employee/{id}/update")
	public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable("id") Long id) {
		return new ResponseEntity<>(employeeService.updateEmployee(employeeDto, id), HttpStatus.OK);
	}

	// delete employee data from the db
	@DeleteMapping("employee/{id}/delete")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id) {
		employeeService.deleteEmployeeId(id);
		return new ResponseEntity<>("Employee deleted", HttpStatus.OK);
	}
}