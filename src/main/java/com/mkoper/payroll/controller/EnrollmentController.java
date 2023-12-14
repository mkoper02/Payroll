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

import com.mkoper.payroll.dto.EnrollmentDto;
import com.mkoper.payroll.service.EnrollmentService;

@RestController
public class EnrollmentController {
    
    @Autowired
    private EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // get all entrollments in the db
    @GetMapping("enrollemnt")
    public List<EnrollmentDto> getEntrollments() {
        return enrollmentService.getEntrollments();
    }

    // get enrollment of employee with given ID
    @GetMapping("employee/{employeeId}/enrollment")
    public ResponseEntity<EnrollmentDto> getEnrollemntByemployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(enrollmentService.getByEmployeeId(employeeId));
    } 

    // create enrollment for employee with given ID
    @PostMapping("enrollment/create")
    public ResponseEntity<EnrollmentDto> addEnrollment(@RequestBody EnrollmentDto enrollmentDto) {
        return new ResponseEntity<>(enrollmentService.saveEnrollment(enrollmentDto), HttpStatus.OK);
    }

    // update enrollment of employee with given ID
    @PutMapping("enrollment/update/{employeeId}")
    public ResponseEntity<EnrollmentDto> updateEnrollment(@RequestBody EnrollmentDto enrollmentDto, @PathVariable Long employeeId) {
        return new ResponseEntity<>(enrollmentService.updateEnrollment(enrollmentDto, employeeId), HttpStatus.OK);
    }
}
