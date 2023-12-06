package com.mkoper.payroll.dto;

import java.time.LocalDate;

public class PayrollRaportDto {
    private Long id;
    private LocalDate date;
    private Float bonus;
    private Float totalAmount;

    public PayrollRaportDto() {}

    public PayrollRaportDto(Long id, LocalDate date, Float bonus, Float totalAmount) {
        this.id = id;
        this.date = date;
        this.bonus = bonus;
        this.totalAmount = totalAmount;
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

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }    
}
