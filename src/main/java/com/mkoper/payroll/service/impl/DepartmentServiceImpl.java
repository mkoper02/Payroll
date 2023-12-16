package com.mkoper.payroll.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.mkoper.payroll.dto.DepartmentDto;
import com.mkoper.payroll.exceptions.DepartmentNotFoundException;
import com.mkoper.payroll.model.Department;
import com.mkoper.payroll.repository.DepartmentRepository;
import com.mkoper.payroll.service.DepartmentService;

public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentDto> getAll() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map((department) -> mapTodDepartmentDto(department)).collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getByDepartmentId(Long departmentId) {
        return mapTodDepartmentDto(departmentRepository.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException("Department could not be found!")));
    }

    @Override
    public DepartmentDto getByDepartmentName(String departmentName) {
        return mapTodDepartmentDto(departmentRepository.findByName(departmentName).orElseThrow(() -> new DepartmentNotFoundException("Department could not be found!")));
    }

    @Override
    public DepartmentDto updateDepartment(DepartmentDto departmentDto) {
        if (departmentDto.getId() == null) {
            throw new IllegalArgumentException("ID was not given!");
        }
        
        Department department = departmentRepository.findById(departmentDto.getId()).orElseThrow(() -> new DepartmentNotFoundException("Department could not be found!"));
        if (departmentDto.getDepratmentName() != null) department.setName(departmentDto.getDepratmentName());
        if (departmentDto.getCountry() != null) department.setCountry(departmentDto.getCountry());
        if (departmentDto.getCity() != null) department.setCity(departmentDto.getCity());
        if (departmentDto.getStreet() != null) department.setStreet(departmentDto.getStreet());

        return mapTodDepartmentDto(departmentRepository.save(department));
    }

    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        departmentRepository.delete(departmentRepository.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException("Department could not be found!")));
    }
    
    private DepartmentDto mapTodDepartmentDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();

        departmentDto.setId(department.getId());
        departmentDto.setDepratmentName(department.getName());
        departmentDto.setCountry(department.getCountry());
        departmentDto.setCity(department.getCity());
        departmentDto.setStreet(department.getStreet());

        return departmentDto;
    }
}