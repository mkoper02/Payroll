package com.mkoper.payroll.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "hourly_wage", nullable = false)
    private Float hourlyWage;

    @Column(name = "contract_type", nullable = false) 
    private String contractType;

    @Column(name = "hours_per_week", nullable = true) 
    private Integer hours;

    // FOREIGN KEYS
    // relation with employee table (share primary key)
    @JsonIgnore
    @OneToOne 
    @MapsId
    private Employee employee;

    // relation with payroll_raport
    @JsonIgnore
    @OneToMany(mappedBy = "salary")
    private List<PayrollRaport> payrollRaports;

    public Salary() {}

    public Salary(Long id, Float hourlyWage, String contractType, Integer hours, Employee employee, List<PayrollRaport> payrollRaports) {
        this.id = id;
        this.hourlyWage = hourlyWage;
        this.contractType = contractType;
        this.hours = hours;
        this.employee = employee;
        this.payrollRaports = payrollRaports;
    }

    public Salary(Float hourlyWage, String contractType, Integer hours, Employee employee, List<PayrollRaport> payrollRaports) {
        this.hourlyWage = hourlyWage;
        this.contractType = contractType;
        this.hours = hours;
        this.employee = employee;
        this.payrollRaports = payrollRaports;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getHourlyWage() {
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<PayrollRaport> getPayrollRaports() {
        return payrollRaports;
    }

    public void setPayrollRaports(List<PayrollRaport> payrollRaports) {
        this.payrollRaports = payrollRaports;
    }
}
