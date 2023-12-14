package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.UserDto;
import com.mkoper.payroll.service.UserService;

@RestController
public class UserController {

    @Autowired 
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping("user")
    public List<UserDto> getUsers() {
        return userService.getAll();
    }

    // Get user with given ID
    @GetMapping("user/id={userId}")
    public ResponseEntity<UserDto> getUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    // Get user with given username
    @GetMapping("user/{username}")
    public ResponseEntity<UserDto> getUserUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
}
