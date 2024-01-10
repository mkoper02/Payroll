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

import com.mkoper.payroll.dto.PositionDto;
import com.mkoper.payroll.model.Position;
import com.mkoper.payroll.service.PositionService;

@RestController
@CrossOrigin
public class PositionController {
    
    @Autowired
    private PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    // get all positions
    @GetMapping("position")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<PositionDto> getPositions() {
        return positionService.getAll();
    }

    // get positions in given department
    @GetMapping("position/department/{departmentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<PositionDto> getPositionsByDepartmentId(@PathVariable Long departmentId) {
        return positionService.getByDepartmentId(departmentId);
    }

    // get position of employee with given ID
    @GetMapping("employee/{employeeId}/position")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public ResponseEntity<PositionDto> getPositionByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(positionService.getByEmployeeId(employeeId));
    }

    // get position with given ID
    @GetMapping("position/{positionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<PositionDto> getPositionById(@PathVariable Long positionId) {
        return ResponseEntity.ok(positionService.getByPositionId(positionId));
    }
    
    // create new position
    @PostMapping("position/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Position> addPosition(@RequestBody Position position) {
        return new ResponseEntity<>(positionService.savePosition(position), HttpStatus.CREATED);
    }

    // update position with given ID
    @PutMapping("position/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PositionDto> updatePosition(@RequestBody PositionDto positionDto) {
        return new ResponseEntity<>(positionService.updatePosition(positionDto), HttpStatus.OK);
    }

    // delete position with given ID
    @DeleteMapping("position/delete/{positionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePosition(@PathVariable Long positionId) {
        positionService.deletePosition(positionId);
        return new ResponseEntity<>("Position deleted", HttpStatus.OK);
    }
}
