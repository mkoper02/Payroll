package com.mkoper.payroll.dto;

import java.util.List;

import com.mkoper.payroll.model.Role;

public class UserDto {
    private Long id;
    private String username;
    private List<Role> roles;

    public UserDto() {}

    public UserDto(Long id, String username, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setToles(List<Role> roles) {
        this.roles = roles;
    }
}
