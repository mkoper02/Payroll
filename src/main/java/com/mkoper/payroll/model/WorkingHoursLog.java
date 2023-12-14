package com.mkoper.payroll.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class WorkingHoursLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "hours_worked", nullable = false)
    private Integer hoursWorked;
    
    @Column(name = "date", nullable = false)
    private LocalDate date;

    
    // FOREIGN KEYS
    // relation with employee table
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // relation with payroll_raport
    @JsonIgnore
    @OneToOne(mappedBy = "workingLog")
    private PayrollRaport payrollRaport;

    public WorkingHoursLog() {}

    public WorkingHoursLog(Long id, Integer hoursWorked, LocalDate date, Employee employee, PayrollRaport payrollRaport) {
        this.id = id;
        this.hoursWorked = hoursWorked;
        this.date = date;
        this.employee = employee;
        this.payrollRaport = payrollRaport;
    }

    public WorkingHoursLog(Integer hoursWorked, LocalDate date, Employee employee, PayrollRaport payrollRaport) {
        this.hoursWorked = hoursWorked;
        this.date = date;
        this.employee = employee;
        this.payrollRaport = payrollRaport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Integer hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public PayrollRaport getPayrollRaport() {
        return payrollRaport;
    }

    public void setPayrollRaport(PayrollRaport payrollRaport) {
        this.payrollRaport = payrollRaport;
    }

    @Override
    public String toString() {
        return "WorkingHoursLog [id=" + id + ", hoursWorked=" + hoursWorked + ", date=" + date + ", employee=" + employee + ", payrollRaport=" + payrollRaport + "]";
    }
}
