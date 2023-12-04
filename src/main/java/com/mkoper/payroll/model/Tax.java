package com.mkoper.payroll.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Tax {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", length = 50, nullable = false)
    private String taxName;
    
    @Column(name = "tax_amount", nullable = false)
    private Float taxAmount;
    
    // FOREIGN KEYS
    // relation with salary table
    @ManyToMany(mappedBy = "salaryTaxes")
    private List<Salary> taxedSalary;    

    public Tax() {}

    public Tax(Long id, String taxName, Float taxAmount, List<Salary> taxedSalary) {
        this.id = id;
        this.taxName = taxName;
        this.taxAmount = taxAmount;
        this.taxedSalary = taxedSalary;
    }

    public Tax(String taxName, Float taxAmount, List<Salary> taxedSalary) {
        this.taxName = taxName;
        this.taxAmount = taxAmount;
        this.taxedSalary = taxedSalary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public Float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public List<Salary> getTaxedSalary() {
        return taxedSalary;
    }

    public void setTaxedSalary(List<Salary> taxedSalary) {
        this.taxedSalary = taxedSalary;
    }

    @Override
    public String toString() {
        return "Tax [id=" + id + ", taxName=" + taxName + ", taxAmount=" + taxAmount + ", taxedSalary=" + taxedSalary + "]";
    }
}
