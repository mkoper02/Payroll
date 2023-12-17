package com.mkoper.payroll.dto;

import java.util.List;

public class DepartmentDto {
    private Long id;
    private String depratmentName;
    private String country;
    private String city;
    private String street;
    private List<String> positions;
    
    public DepartmentDto() {};

    public DepartmentDto(Long id, String depratmentName, String country, String city, String street, List<String> positions) {
        this.id = id;
        this.depratmentName = depratmentName;
        this.country = country;
        this.city = city;
        this.street = street;
        this.positions = positions;
    }

    public String getDepratmentName() {
        return depratmentName;
    }

    public void setDepratmentName(String depratmentName) {
        this.depratmentName = depratmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }
}
