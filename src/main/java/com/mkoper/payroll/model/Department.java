package com.mkoper.payroll.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    
    @Column(length = 50, nullable = false)
    private String name;
    
    @Column(length = 50, nullable = false) 
    private String country;
    
    @Column(length = 50, nullable = false) 
    private String city;
    
    @Column(length = 50, nullable = false) 
    private String street;
    
    // FOREIGN KEYS
    // relation with posiotion table
    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Position> jobPositions;
    
    public Department() {}

    public Department(Long id, String name, String country, String city, String street, List<Position> jobPositions) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.street = street;
        this.jobPositions = jobPositions;
    }
    
    public Department(String name, String country, String city, String street, List<Position> jobPositions) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
