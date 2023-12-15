package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.PositionDto;
import com.mkoper.payroll.model.Position;

public interface PositionService {
    // return all positions 
    public List<PositionDto> getAll();

    // get positions in given department
    public List<PositionDto> getByDepartmentId(Long departmentId);

    // get position of employee wih given ID
    public PositionDto getByEmployeeId(Long employeeId);

    // update position with given ID
    public PositionDto updatePosition(PositionDto posiotionDto);

    // save new position to the db
    public Position savePosition(Position position);

    // delete posiotion from the db
    public void deletePosition(Long positionId);
}
