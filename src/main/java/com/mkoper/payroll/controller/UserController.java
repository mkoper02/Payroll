package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.UserDto;
import com.mkoper.payroll.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping("")
    public List<UserDto> getUsers() {
        return userService.getAll();
    }

    // Get user with given ID
    @GetMapping("/id={userId}")
    public ResponseEntity<UserDto> getUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserUsername(@PathVariable("username") String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
}
