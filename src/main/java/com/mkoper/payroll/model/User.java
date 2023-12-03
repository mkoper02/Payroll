package com.mkoper.payroll.model;

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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne 
    @MapsId
    private Employee employee;

    @Column(length = 30, nullable = false) 
    private String username;

    @Column(length = 30, nullable = false) 
    private String password;

    @Column(name = "access_level", length = 50, nullable = true) 
    private String accessLevel;
    
    public User() {}
    
    public User(Long id, String username, String password, String accessLevel) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
    }    
    
    public User(String username, String password, String accessLevel) {
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAccessLevel() {
        return accessLevel;
    }
    
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", accessLevel=" + accessLevel + ", employee=" + employee + "]";
    }
}