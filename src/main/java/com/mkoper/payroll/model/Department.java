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
    
    // FOREIGN KEYS
    // relation with posiotion table
    @OneToMany(mappedBy = "department")
    private List<Position> jobPositions;
    
    public Department(Long id, String departmentName, String country, String city, String street, List<Position> jobPositions) {
        this.id = id;
        this.departmentName = departmentName;
        this.country = country;
        this.city = city;
        this.street = street;
        this.jobPositions = jobPositions;
    }
    
    public Department(String departmentName, String country, String city, String street, List<Position> jobPositions) {
        this.departmentName = departmentName;
        this.country = country;
        this.city = city;
        this.street = street;
        this.jobPositions = jobPositions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<Position> getJobPositions() {
        return jobPositions;
    }

    public void setJobPositions(List<Position> jobPositions) {
        this.jobPositions = jobPositions;
    }

    @Override
    public String toString() {
        return "Department [id=" + id + ", departmentName=" + departmentName + ", country=" + country + ", city=" + city + ", street=" + street + ", jobPositions=" + jobPositions + "]";
    }
}
