package com.mkoper.payroll.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    
    @Column(name = "gross_salary", nullable = false)
    private Float grossSalary;
    
    @Column(name = "net_salary", nullable = false)
    private Float netSalary;

    // FOREIGN KEYS
    // relation with employee table (share primary key)
    @JsonIgnore
    @OneToOne 
    @MapsId
    private Employee employee;

    // relation with enrollment table
    @OneToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    // relation with tax table
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "tax_salary",
        joinColumns = @JoinColumn(name = "salary_id"),
        inverseJoinColumns = @JoinColumn(name = "tax_id")
    )
    private List<Tax> salaryTaxes;

    // relation with payroll_raport
    @OneToMany(mappedBy = "salary")
    private List<PayrollRaport> payrollRaports;

    public Salary() {}

    public Salary(Long id, Float grossSalary, Float netSalary, Employee employee, Enrollment enrollment, List<Tax> salaryTaxes, List<PayrollRaport> payrollRaports) {
        this.id = id;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
        this.employee = employee;
        this.enrollment = enrollment;
        this.salaryTaxes = salaryTaxes;
        this.payrollRaports = payrollRaports;
    }

    public Salary(Float grossSalary, Float netSalary, Employee employee, Enrollment enrollment, List<Tax> salaryTaxes, List<PayrollRaport> payrollRaports) {
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
        this.employee = employee;
        this.enrollment = enrollment;
        this.salaryTaxes = salaryTaxes;
        this.payrollRaports = payrollRaports;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public List<Tax> getSalaryTaxes() {
        return salaryTaxes;
    }

    public void setSalaryTaxes(List<Tax> salaryTaxes) {
        this.salaryTaxes = salaryTaxes;
    }

    public List<PayrollRaport> getPayrollRaports() {
        return payrollRaports;
    }

    public void setPayrollRaports(List<PayrollRaport> payrollRaports) {
        this.payrollRaports = payrollRaports;
    }

    @Override
    public String toString() {
        return "Salary [id=" + id + ", grossSalary=" + grossSalary + ", netSalary=" + netSalary + ", employee=" + employee + ", enrollment=" + enrollment + ", salaryTaxes=" + salaryTaxes + ", payrollRaports=" + payrollRaports + "]";
    }
}
