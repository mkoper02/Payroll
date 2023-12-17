package com.mkoper.payroll.dto;

public class SalaryDto {
    private Long employeeId;
    private Float grossSalary;
    private String contractType;
    private Integer hours;

    public SalaryDto() {}

    public SalaryDto(Long employeeId, Float grossSalary, String contractType, Integer hours) {
        this.employeeId = employeeId;
        this.grossSalary = grossSalary;
        this.contractType = contractType;
        this.hours = hours;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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
