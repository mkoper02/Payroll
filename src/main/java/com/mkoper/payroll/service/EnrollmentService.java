package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.EnrollmentDto;

public interface EnrollmentService {
    // get all enrollments
    public List<EnrollmentDto> getEntrollments();

    // get enrollemnt with given employee ID
    public EnrollmentDto getByEmployeeId(Long employeeId);

    // update enrollment with given employee ID
    public EnrollmentDto updateEnrollment(EnrollmentDto enrollment, Long employeeId);

    // save new enrollment to the db
    public EnrollmentDto saveEnrollment(EnrollmentDto enrollment);

    // delete enrollment from the db
    public void deleteEnrollment(Long enrollmentId);
}
