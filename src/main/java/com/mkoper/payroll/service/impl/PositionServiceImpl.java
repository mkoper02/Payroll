package com.mkoper.payroll.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.dto.PositionDto;
import com.mkoper.payroll.exceptions.DepartmentNotFoundException;
import com.mkoper.payroll.exceptions.EmployeeNotFoundException;
import com.mkoper.payroll.exceptions.PositionNotFoundException;
import com.mkoper.payroll.model.Employee;
import com.mkoper.payroll.model.Position;
import com.mkoper.payroll.repository.DepartmentRepository;
import com.mkoper.payroll.repository.EmployeeRepository;
import com.mkoper.payroll.repository.PositionRepository;
import com.mkoper.payroll.service.PositionService;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired private PositionRepository positionRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private EmployeeRepository employeeRepository;

    public PositionServiceImpl(PositionRepository positionRepository, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.positionRepository = positionRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<PositionDto> getAll() {
        List<Position> positions = positionRepository.findAll();
        return positions.stream().map((position) -> mapToPosiotionDto(position)).collect(Collectors.toList());
    }

    @Override
    public List<PositionDto> getByDepartmentId(Long departmentId) {
        List<Position> positions = positionRepository.findByDepartment(departmentRepository.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException("Department could not be found!")));
        return positions.stream().map((position) -> mapToPosiotionDto(position)).collect(Collectors.toList());
    }

    @Override
    public PositionDto getByPositionId(Long positionId) {
        return mapToPosiotionDto(positionRepository.findById(positionId).orElseThrow(() -> new DepartmentNotFoundException("Department could not be found!")));
    }

    @Override
    public PositionDto getByPositionName(String positionName) {
        return mapToPosiotionDto(positionRepository.findByName(positionName).orElseThrow(() -> new DepartmentNotFoundException("Department could not be found!")));
    }

    @Override
    public PositionDto getByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee could not be found!"));
        Position position = positionRepository.findById(employee.getJobPosition().getId()).get();

        return mapToPosiotionDto(position);
    }

    @Override
    public PositionDto updatePosition(PositionDto posiotionDto) {
        if (posiotionDto.getId() == null) {
            throw new IllegalArgumentException("ID was not given!");
        }

        Position position = positionRepository.findById(posiotionDto.getId()).orElseThrow(() -> new PositionNotFoundException("Position could not be found!"));

        // TODO:
        // if (posiotionDto.getDepartmentName() != null) position.setDepartment(departmentRepository.findByName(posiotionDto.getDepartmentName()).orElseThrow(() -> new DepartmentNotFoundException("Department could not be found!")));
        if (posiotionDto.getName() != null) position.setName(posiotionDto.getName());

        return mapToPosiotionDto(positionRepository.save(position));
    }

    @Override
    public Position savePosition(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public void deletePosition(Long positionId) {
        positionRepository.delete(positionRepository.findById(positionId).orElseThrow(() -> new PositionNotFoundException("Position could not be found!")));
    }
    
    private PositionDto mapToPosiotionDto(Position position) {
        PositionDto positionDto = new PositionDto();

        positionDto.setId(position.getId());
        positionDto.setName(position.getName());
        positionDto.setDepartmentName(position.getDepartment().getName());

        return positionDto;
    }
}
