package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.model.User;
import com.mkoper.payroll.service.UserService;

@RestController
@RequestMapping(path = "user")
public class UserController {
    
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("getall")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("getid")
    public User getUserId(Long id) {
        return userService.getUserId(id);
    }

    @GetMapping("getusername")
    public User getUserUsername(String username) {
        return userService.getUserUsername(username);
    }

    @GetMapping("getpermissions")
    public List<User> getUsersPermissionList(String accessLevel) {
        return userService.getUsersPermissionList(accessLevel);
    }

    @PostMapping("add")
    public User addUser(@RequestBody User user) {        
        return userService.saveUser(user);
    }

    @PostMapping("addauto") 
    public User addUserGeneratePass(@RequestBody Long user_id) {
        return userService.saveUserGeneratePass(user_id);
    }
}