package com.mkoper.payroll.dto;

public class SalaryDto {
    private Long id;
    private Float grossSalary;
    private Float netSalary;
    
    public SalaryDto() {}

    public SalaryDto(Long id, Float grossSalary, Float netSalary) {
        this.id = id;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
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

    public Float getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Float netSalary) {
        this.netSalary = netSalary;
    }
}
