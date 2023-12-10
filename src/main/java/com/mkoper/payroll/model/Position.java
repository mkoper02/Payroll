package com.mkoper.payroll.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Position {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    // FOREIGN KEYS
    // relation with department table
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    private Department department;

    // relation with employee table
    @JsonIgnore
    @OneToMany(mappedBy = "jobPosition")
    private List<Employee> employees;
    
    public Position() {}

    public Position(Long id, String name, Department department, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.employees = employees;
    }

    public Position(String name, Department department, List<Employee> employees) {
        this.name = name;
        this.department = department;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
