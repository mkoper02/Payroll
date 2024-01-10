package com.mkoper.payroll.dto;

public class BenefitDto {
    private String name;
    private Float cost;

    public BenefitDto() {}

    public BenefitDto(String name, Float cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }
}
