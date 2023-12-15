package com.mkoper.payroll.dto;

public class SalaryDto {
    private Long id;
    private Float grossSalary;
    private String contractType;
    private Integer hours;

    public SalaryDto() {}

    public SalaryDto(Long id, Float grossSalary, String contractType, Integer hours) {
        this.id = id;
        this.grossSalary = grossSalary;
        this.contractType = contractType;
        this.hours = hours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(Float grossSalary) {
        this.grossSalary = grossSalary;
    }
        
    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }
}
