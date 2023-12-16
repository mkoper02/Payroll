package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.DepartmentDto;
import com.mkoper.payroll.model.Department;
import com.mkoper.payroll.service.DepartmentService;

@RestController
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // get all departments
    @GetMapping("department")
    public List<DepartmentDto> getDepartments() {
        return departmentService.getAll();
    }

    @GetMapping("department/{departmentId}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long departmentId) {
        return ResponseEntity.ok(departmentService.getByDepartmentId(departmentId));
    }

    @GetMapping("department/{departmentName}")
    public ResponseEntity<DepartmentDto> getDepartmentByName(@PathVariable String departmentName) {
        return ResponseEntity.ok(departmentService.getByDepartmentName(departmentName));
    }

    @PutMapping("department/update")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody DepartmentDto departmentDto) {
        return new ResponseEntity<>(departmentService.updateDepartment(departmentDto), HttpStatus.OK);
    }

    @PostMapping("department/create")
    public ResponseEntity<Department> addDepartment(@RequestBody Department departmentDto) {
        return new ResponseEntity<>(departmentService.saveDepartment(departmentDto), HttpStatus.OK);
    }
}
