package com.mkoper.payroll.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.dto.EnrollmentDto;
import com.mkoper.payroll.exceptions.EmployeeNotFoundException;
import com.mkoper.payroll.model.Enrollment;
import com.mkoper.payroll.repository.EmployeeRepository;
import com.mkoper.payroll.repository.EnrollmentRepository;
import com.mkoper.payroll.service.EnrollmentService;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired private EnrollmentRepository enrollmentRepository;
    @Autowired private EmployeeRepository employeeRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, EmployeeRepository employeeRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EnrollmentDto> getEntrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return enrollments.stream().map((enrollment) -> mapToEnrollmentDto(enrollment)).collect(Collectors.toList());
    }

    @Override
    public EnrollmentDto getByEmployeeId(Long employeeId) {
        Enrollment enrollment = enrollmentRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Enrollment for employee with given ID could not be found!"));

        return mapToEnrollmentDto(enrollment);
    }

    @Override
    public EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto, Long employeeId) {
        Enrollment enrollment = enrollmentRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Enrollment for employee with given ID could not be found!"));

        enrollment.setContractType(enrollmentDto.getContractType());
        enrollment.setHours(enrollmentDto.getHours());

        return mapToEnrollmentDto(enrollmentRepository.save(enrollment));
    }

    @Override
    public EnrollmentDto saveEnrollment(EnrollmentDto enrollmentDto) {
        Enrollment enrollment = new Enrollment();

        enrollment.setId(enrollmentDto.getId());
        enrollment.setContractType(enrollmentDto.getContractType());
        enrollment.setHours(enrollmentDto.getHours());
        enrollment.setJoinDate(LocalDate.now());
        enrollment.setEmployee(employeeRepository.findById(enrollmentDto.getId()).get());

        // Set salary happens in SalaryServiceImpl when saving salary 
        // enrollment.setSalary(null);

        return mapToEnrollmentDto(enrollmentRepository.save(enrollment));
    }

    @Override
    public void deleteEnrollment(Long employeeId) {
        Enrollment enrollment = enrollmentRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Enrollment for employee with given ID could not be found!"));
        enrollmentRepository.delete(enrollment);
    }

    private EnrollmentDto mapToEnrollmentDto(Enrollment enrollment) {
        EnrollmentDto enrollmentDto = new EnrollmentDto();

        enrollmentDto.setId(enrollment.getId());
        enrollmentDto.setHours(enrollment.getHours());
        enrollmentDto.setContractType(enrollment.getContractType());

        return enrollmentDto;
    }
}
