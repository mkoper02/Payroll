package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    // Get user with given username or ID
    @GetMapping("user/{userString}")
    public ResponseEntity<UserDto> getUserUsername(@PathVariable("userString") String userString) {
        try {
            return ResponseEntity.ok(userService.getUserById(Long.parseLong(userString)));
        } catch (Exception e) {
            return ResponseEntity.ok(userService.getUserByUsername(userString));
        }
    }

    @DeleteMapping("user/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUserId(userId);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
