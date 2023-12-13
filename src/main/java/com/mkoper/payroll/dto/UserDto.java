package com.mkoper.payroll.dto;

import java.util.List;

public class UserDto {
    private Long id;
    private String username;
    private List<String> role;

    public UserDto() {}

    public UserDto(Long id, String username, List<String> role) {
        this.id = id;
        this.username = username;
        this.role = role;
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

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
