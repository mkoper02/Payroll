package com.mkoper.payroll.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    // private static final long serialVerionUID = 1;

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
