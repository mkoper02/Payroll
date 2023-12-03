package com.mkoper.payroll.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String departmentName;
    
    @Column(length = 50, nullable = false) 
    private String country;

    @Column(length = 50, nullable = false) 
    private String city;

    @Column(length = 50, nullable = false) 
    private String street;

    // Foreign key
    @OneToMany(mappedBy = "department")
    private List<Position> jobPositions;
}
