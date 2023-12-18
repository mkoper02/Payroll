package com.mkoper.payroll.exceptions;

public class BenefitNotFoundException extends RuntimeException {
 
    public BenefitNotFoundException(String message) {
        super(message);
    }
}
