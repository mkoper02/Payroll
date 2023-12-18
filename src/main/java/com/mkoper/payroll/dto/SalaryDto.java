package com.mkoper.payroll.dto;

public class SalaryDto {
    private Long employeeId;
    private Float hourlyWage;
    private String contractType;
    private Integer hours;

    public SalaryDto() {}

    public SalaryDto(Long employeeId, Float hourlyWage, String contractType, Integer hours) {
        this.employeeId = employeeId;
        this.hourlyWage = hourlyWage;
        this.contractType = contractType;
        this.hours = hours;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Float getHourlWage() {
        return hourlyWage;
    }

    public void setHourlyWage(Float hourlyWage) {
        this.hourlyWage = hourlyWage;
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
