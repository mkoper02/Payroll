package com.mkoper.payroll.exceptions;

public class TaxNotFoundException extends RuntimeException {
    
    public TaxNotFoundException(String message) {
        super(message);
    }
}
