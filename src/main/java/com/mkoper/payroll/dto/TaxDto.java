package com.mkoper.payroll.dto;

public class TaxDto {
    private String name;
    private Float amount;

    public TaxDto() {}

    public TaxDto(String name, Float amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
