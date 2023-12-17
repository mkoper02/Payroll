package com.mkoper.payroll.exceptions;

public class WorkingLogNotFoundException extends RuntimeException {
    public WorkingLogNotFoundException(String message) {
        super(message);
    }
}
