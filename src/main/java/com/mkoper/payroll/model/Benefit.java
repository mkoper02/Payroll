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
public class Benefit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", length = 50, nullable = false)
    private String benefitName;
    
    @Column(name = "cost", nullable = false)
    private Float benefitCost;
    
    // FOREIGN KEYS
    // relation with payroll_raport table
    @ManyToMany(mappedBy = "payrollraportBenefits")
    private List<PayrollRaport> benefitsPayrollraport;
    
    public Benefit(Long id, String benefitName, Float benefitCost, List<PayrollRaport> benefitsPayrollraport) {
        this.id = id;
        this.benefitName = benefitName;
        this.benefitCost = benefitCost;
        this.benefitsPayrollraport = benefitsPayrollraport;
    }
    
    public Benefit(String benefitName, Float benefitCost, List<PayrollRaport> benefitsPayrollraport) {
        this.benefitName = benefitName;
        this.benefitCost = benefitCost;
        this.benefitsPayrollraport = benefitsPayrollraport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBenefitName() {
        return benefitName;
    }

    public void setBenefitName(String benefitName) {
        this.benefitName = benefitName;
    }

    public Float getBenefitCost() {
        return benefitCost;
    }

    public void setBenefitCost(Float benefitCost) {
        this.benefitCost = benefitCost;
    }

    public List<PayrollRaport> getBenefitsPayrollraport() {
        return benefitsPayrollraport;
    }

    public void setBenefitsPayrollraport(List<PayrollRaport> benefitsPayrollraport) {
        this.benefitsPayrollraport = benefitsPayrollraport;
    }

    @Override
    public String toString() {
        return "Benefit [id=" + id + ", benefitName=" + benefitName + ", benefitCost=" + benefitCost + ", benefitsPayrollraport=" + benefitsPayrollraport + "]";
    }
}
