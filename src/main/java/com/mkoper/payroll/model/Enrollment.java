package com.mkoper.payroll.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "join_date", nullable = false) 
    @CreationTimestamp
    private LocalDate joinDate;
    
    @Column(name = "contract_type", length = 50, nullable = false) 
    private String contractType;
    
    @Column(name = "hours_per_month", nullable = false) 
    private Integer hours;  
    
    // FOREIGN KEYS      
    // relation with employee table (shared primary key)
    @JsonIgnore
    @OneToOne
    @MapsId
    private Employee employee;

    // relation with salary table
    @OneToOne(mappedBy = "enrollment")
    private Salary salary;
    
    public Enrollment() {}
    
    public Enrollment(Long id, String contractType, Integer hours, LocalDate joinDate) {
        this.id = id;
        this.contractType = contractType;
        this.hours = hours;
        this.joinDate = joinDate;
    }

    public Enrollment(String contractType, Integer hours, LocalDate joinDate) {
        this.contractType = contractType;
        this.hours = hours;
        this.joinDate = joinDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }
   
    @Override
    public String toString() {
        return "Enrollment [id=" + id + ", joinDate=" + joinDate + ", contractType=" + contractType + ", hours=" + hours + ", employee=" + employee + ", salary=" + salary + "]";
    }
}
