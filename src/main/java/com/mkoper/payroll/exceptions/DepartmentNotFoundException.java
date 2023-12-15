package com.mkoper.payroll.exceptions;

public class DepartmentNotFoundException extends RuntimeException {
    
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
