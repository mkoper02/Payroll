package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.UserDto;

public interface UserService {
    // Return all users
    public List<UserDto> getAll();
    
    // Return user with given ID
    public UserDto getUserById(Long userId);

    // Return user with given username
    public UserDto getUserByUsername(String username);

    // Delete user with given ID
    public void deleteUserId(Long userId);
}
