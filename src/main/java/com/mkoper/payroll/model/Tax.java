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

    // Foreign key
    @ManyToMany(mappedBy = "salaryTaxes")
    private List<Salary> taxedSalary;

    @Column(name = "name", length = 50, nullable = false)
    private String taxName;

    @Column(name = "tax_amount", nullable = false)
    private Float taxAmount;
    
}
