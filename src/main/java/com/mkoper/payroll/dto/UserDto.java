package com.mkoper.payroll.dto;

import com.mkoper.payroll.model.Role;

public class UserDto {
    private Long id;
    private String username;
    private Role accessLevel;

    public UserDto() {}

    public UserDto(Long id, String username, Role accessLevel) {
        this.id = id;
        this.username = username;
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

    public Role getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Role accessLevel) {
        this.accessLevel = accessLevel;
    }
}
