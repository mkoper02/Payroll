package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.EmployeeDto;
import com.mkoper.payroll.model.Employee;
import com.mkoper.payroll.service.EmployeeService;

@RestController
@CrossOrigin
public class EmployeeController {
    
	@Autowired 
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// get all employees in the db
	@GetMapping("employee")
	@PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
	public List<EmployeeDto> getEmployees() {
		return employeeService.getAll();
	}
	
	// get employee with given id
	@GetMapping("employee/{employeeId}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
	public ResponseEntity<EmployeeDto> getEmployeeId(@PathVariable Long employeeId) {
		return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
	}

	// get list of employees at position with given ID
	@GetMapping("position/{positionId}/employees")
	@PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
	public List<EmployeeDto> getEmployeesByPositionId(@PathVariable Long positionId) {
		return employeeService.getEmployeesByPositionId(positionId);
	}

	// add employee to the db
	@PostMapping("employee/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
	}

	// update employee data in the db
	@PutMapping("employee/update")
	@PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
	public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto) {
		return new ResponseEntity<>(employeeService.updateEmployee(employeeDto), HttpStatus.OK);
	}

	// delete employee data from the db
	@DeleteMapping("employee/delete/{employeeId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") Long employeeId) {
		employeeService.deleteEmployeeId(employeeId);
		return new ResponseEntity<>("Employee deleted", HttpStatus.OK);
	}
}