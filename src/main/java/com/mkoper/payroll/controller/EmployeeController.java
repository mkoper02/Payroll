package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.model.Employee;
import com.mkoper.payroll.service.EmployeeService;

@RestController
@RequestMapping(path = "employee")
public class EmployeeController {
    
	@Autowired
	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// get all employees in the db
	@GetMapping("getall")
	public List<Employee> getEmployees() {
		return employeeService.getEmployees();
	}

	// get employee with given id
	@GetMapping("getid")
	public Employee getEmployeeId(Long id) {
		return employeeService.getEmployeeId(id);
	}

	// get employees with given first name
	@GetMapping("getfirstname")
	public List<Employee> getEmployeeFirstName(String firstName) {
		return employeeService.getEmployeeFirstName(firstName);
	}

	// add employee to the db
	@PostMapping("add")
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}
}