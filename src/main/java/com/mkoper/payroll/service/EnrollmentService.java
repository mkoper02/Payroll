package com.mkoper.payroll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.model.Enrollment;
import com.mkoper.payroll.repository.EnrollmentRepository;

@Service
public class EnrollmentService {
    @Autowired
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    } 

    public List<Enrollment> getEntrollments() {
        return enrollmentRepository.findAll();
    }
}
