package com.mkoper.payroll.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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

@Entity
@Table
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

    // FOREIGN KEYS
    // relation with users table
    @JsonIgnore
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "user_id")
    private User user;

    // relation with enrollment table
    @JsonIgnore
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    // relation with salary table
    @JsonIgnore
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
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

    public Employee(Long id, String firstName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber, String country, String city, String street) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public Employee(String firstName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber, String country, String city, String street) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = this.id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
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

    @Override
    public String toString() {
        return "Employee [id=" + id + ", dateOfBirth=" + dateOfBirth + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", country=" + country + ", city=" + city + ", street=" + street + ", user=" + user + ", enrollment=" + enrollment + "]";
    }
}   