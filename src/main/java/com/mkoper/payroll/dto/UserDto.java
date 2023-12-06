package com.mkoper.payroll.dto;

import com.mkoper.payroll.model.UserAccessLevel;

public class UserDto {
    private Long id;
    private String username;
    private UserAccessLevel accessLevel;

    public UserDto() {}

    public UserDto(Long id, String username, UserAccessLevel accessLevel) {
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

    public UserAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(UserAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }
}
