package com.mkoper.payroll.dto;

import java.util.List;

import com.mkoper.payroll.model.Benefit;

public class PayrollRaportDto {
    private Long id;
    private Long employeeId;
    private Integer year;
    private Integer month;
    private Integer hoursWorked;
    private Float bonus;
    private Float netSalary;
    private Float totalAmount;
    private List<Benefit> benefits;
    private List<TaxDto> taxes;
    
    public PayrollRaportDto() {}
    
    public PayrollRaportDto(Long id, Long employeeId, Integer year, Integer month, Integer hoursWorked, Float bonus, Float netSalary, Float totalAmount, List<Benefit> benefits, List<TaxDto> taxes) {
        this.id = id;
        this.employeeId = employeeId;
        this.year = year;
        this.month = month;
        this.hoursWorked = hoursWorked;
        this.bonus = bonus;
        this.netSalary = netSalary;
        this.totalAmount = totalAmount;
        this.benefits = benefits;
        this.taxes = taxes;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Benefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<Benefit> benefits) {
        this.benefits = benefits;
    }

    public List<TaxDto> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<TaxDto> taxes) {
        this.taxes = taxes;
    }

    public Integer getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Integer hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}