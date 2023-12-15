package com.mkoper.payroll.exceptions;

public class PositionNotFoundException extends RuntimeException {
    
    public PositionNotFoundException(String message) {
        super(message);
    }
}
