package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.PositionDto;
import com.mkoper.payroll.model.Position;
import com.mkoper.payroll.service.PositionService;

@RestController
public class PositionController {
    
    @Autowired
    private PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    // get all positions
    @GetMapping("position")
    public List<PositionDto> getPositions() {
        return positionService.getAll();
    }

    // get position of employee with given ID
    @GetMapping("employee/{employeeId}/position")
    public ResponseEntity<PositionDto> getPositionByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(positionService.getByEmployeeId(employeeId));
    }

    @PostMapping("position/create")
    public ResponseEntity<Position> addPosition(@RequestBody Position position) {
        return new ResponseEntity<>(positionService.savePosition(position), HttpStatus.OK);
    }
}
