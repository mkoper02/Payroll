package com.mkoper.payroll.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "phone_number"), @UniqueConstraint(columnNames = "email")})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date_of_birth", nullable = false) 
    private LocalDate dateOfBirth;
    
    @Column(name = "first_name", length = 50, nullable = false) 
    private String firstName;
    
    @Column(name = "last_name", length = 50, nullable = false) 
    private String lastName;
    
    @Column(length = 50, nullable = false) 
    private String email;
    
    @Column(name = "phone_number", length = 9, nullable = false) 
    private String phoneNumber;
    
    @Column(length = 50, nullable = false) 
    private String country;
    
    @Column(length = 50, nullable = false) 
    private String city;
    
    @Column(length = 50, nullable = false) 
    private String street;

    @Column(name = "join_date", nullable = false) 
    @CreationTimestamp
    private LocalDate joinDate;

    // FOREIGN KEYS
    // relation with salary table
    @JsonIgnore
    @OneToOne(mappedBy = "employee")
    @PrimaryKeyJoinColumn(name = "salary_id")
    private Salary salary;

    // relation with position table
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position jobPosition;

    // relation with working_hours_log table
    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<WorkingHoursLog> workLogs;

    // relation with payroll_raport table
    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<PayrollRaport> payrollRaports;

    public Employee() {}

    public Employee(Long id, String firstName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber, String country, String city, String street, LocalDate joinDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.city = city;
        this.street = street;
        this.joinDate = joinDate;
    }

    public Employee(String firstName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber, String country, String city, String street, LocalDate joinDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.city = city;
        this.street = street;
        this.joinDate = joinDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = this.id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
    
    public Salary getSalary() {
        return salary;
    }
    
    public void setSalary(Salary salary) {
        this.salary = salary;
    }
    
    public Position getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(Position jobPosition) {
        this.jobPosition = jobPosition;
    }

    public List<WorkingHoursLog> getWorkLogs() {
        return workLogs;
    }
    
    public void setWorkLogs(List<WorkingHoursLog> workLogs) {
        this.workLogs = workLogs;
    }
    
    public List<PayrollRaport> getPayrollRaports() {
        return payrollRaports;
    }
    
    public void setPayrollRaports(List<PayrollRaport> payrollRaports) {
        this.payrollRaports = payrollRaports;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }
}   