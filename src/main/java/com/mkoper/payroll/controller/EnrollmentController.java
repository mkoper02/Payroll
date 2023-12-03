package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.model.Enrollment;
import com.mkoper.payroll.service.EnrollmentService;

@RestController
@RequestMapping(path = "enrollemnt")
public class EnrollmentController {
    
    @Autowired
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // get all entrollments in the db
    @GetMapping("getall")
    public List<Enrollment> getEntrollments() {
        return enrollmentService.getEntrollments();
    }
}
