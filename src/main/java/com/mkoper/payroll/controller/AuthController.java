package com.mkoper.payroll.controller;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.LoginDto;
import com.mkoper.payroll.dto.RegisterDto;
import com.mkoper.payroll.model.UserEntity;
import com.mkoper.payroll.repository.EmployeeRepository;
import com.mkoper.payroll.repository.RoleRepository;
import com.mkoper.payroll.repository.UserRepository;

@RestController
@RequestMapping("auth")
public class AuthController {
    
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    // private PasswordEncoder passwordEncoder;
    private EmployeeRepository employeeRepository;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, EmployeeRepository employeeRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setEmployee(employeeRepository.findById(registerDto.getId()).get());
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        // user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Collections.singletonList(roleRepository.findByName("USER").get()));

        userRepository.save(user);

        return new ResponseEntity<>("User registration was successful", HttpStatus.OK);
    }
}
