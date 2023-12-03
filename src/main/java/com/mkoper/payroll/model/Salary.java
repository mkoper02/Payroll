package com.mkoper.payroll.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Salary {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign keys
    @JsonIgnore
    @OneToOne 
    @MapsId
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @ManyToMany
    @JoinTable(
        name = "tax_salary",
        joinColumns = @JoinColumn(name = "salary_id"),
        inverseJoinColumns = @JoinColumn(name = "tax_id")
    )
    private List<Tax> salaryTaxes;

    @Column(name = "gross_salary", nullable = false)
    private Float grossSalary;

    @Column(name = "net_salary", nullable = false)
    private Float netSalary;
}
