package com.mkoper.payroll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.model.User;
import com.mkoper.payroll.repository.EmployeeRepository;
import com.mkoper.payroll.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Return all data in User class
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // Return user data with given id
    public User getUserId(Long id) {
        return userRepository.findById((long)id);
    }

    // Return user data with given username
    public User getUserUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getUsersPermissionList(String accessLevel) {
        return userRepository.findByAccessLevel(accessLevel);
    }

    // Save new user to the db
    public User saveUser(User user) {
        user.setEmployee(employeeRepository.findById((long)user.getId()));
        return userRepository.save(user);
    }

    public User saveUserGeneratePass(Long user_id) {
        User user = new User();

        // Create new user with given ID, default access, 
        user.setId(user_id);
        user.setAccessLevel(null);
        user.setEmployee(employeeRepository.findById((long)user_id));
        user.setUsername(user.getEmployee().getFirstName().substring(0, 3) + user.getEmployee().getLastName().substring(0, 3) + user.getId());
        user.setPassword(user.getEmployee().getFirstName().substring(0, 3) + user.getEmployee().getLastName().substring(0, 3) + user.getId());
        
        return userRepository.save(user);
    }
}
