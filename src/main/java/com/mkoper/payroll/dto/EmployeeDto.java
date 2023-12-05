package com.mkoper.payroll.dto;

import java.time.LocalDate;

import com.mkoper.payroll.model.Position;

public class EmployeeDto {
    private Long id;
    private LocalDate dateOfBirth;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Position jobPosition;

    public EmployeeDto() {}

    public EmployeeDto(Long id, LocalDate dateOfBirth, String firstName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public Position getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(Position jobPosition) {
        this.jobPosition = jobPosition;
    }
}
