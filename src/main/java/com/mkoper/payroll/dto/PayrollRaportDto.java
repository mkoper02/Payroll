package com.mkoper.payroll.dto;

import java.time.LocalDate;

public class PayrollRaportDto {
    private Long id;
    private Long employeeId;
    private LocalDate date;
    private Float bonus;
    private Float netSalary;
    
    public PayrollRaportDto() {}
    
    public PayrollRaportDto(Long id, Long employeeId, LocalDate date, Float bonus, Float netSalary) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.bonus = bonus;
        this.netSalary = netSalary;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public Float getBonus() {
        return bonus;
    }
    
    public void setBonus(Float bonus) {
        this.bonus = bonus;
    }
    
    public Float getNetSalary() {
        return netSalary;
    }
    
    public void setNetSalary(Float netSalary) {
        this.netSalary = netSalary;
    }    
    
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long emloyeeId) {
        this.employeeId = emloyeeId;
    }
}