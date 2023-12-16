package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.DepartmentDto;
import com.mkoper.payroll.model.Department;

public interface DepartmentService {
    // return all departments
    public List<DepartmentDto> getAll();

    // get department with given ID
    public DepartmentDto getByDepartmentId(Long departmentId);

    // get department with given name
    public DepartmentDto getByDepartmentName(String departmentName);

    // update department with given ID
    public DepartmentDto updateDepartment(DepartmentDto departmentDto);

    // save new department to the db
    public Department saveDepartment(Department department);

    // delete position from the db
    public void deleteDepartment(Long departmentId);
}
