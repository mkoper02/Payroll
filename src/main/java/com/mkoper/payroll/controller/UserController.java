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

    // get all users in db
    @GetMapping("getall")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    // get uses with given id
    @GetMapping("getid")
    public User getUserId(Long id) {
        return userService.getUserId(id);
    }

    // get user with given username
    @GetMapping("getusername")
    public User getUserUsername(String username) {
        return userService.getUserUsername(username);
    }

    // get users with given permissions/access level
    @GetMapping("getpermissions")
    public List<User> getUsersPermissionList(String accessLevel) {
        return userService.getUsersPermissionList(accessLevel);
    }

    // add user to the db
    @PostMapping("add")
    public User addUser(@RequestBody User user) {        
        return userService.saveUser(user);
    }

    // add user to the db, password and username will be created automatically
    @PostMapping("addauto") 
    public User addUserGeneratePass(@RequestBody Long user_id) {
        return userService.saveUserGeneratePass(user_id);
    }
}