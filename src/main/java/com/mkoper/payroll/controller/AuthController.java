package com.mkoper.payroll.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.AuthResponseDto;
import com.mkoper.payroll.dto.LoginDto;
import com.mkoper.payroll.dto.RegisterDto;
import com.mkoper.payroll.model.Role;
import com.mkoper.payroll.model.UserEntity;
import com.mkoper.payroll.repository.EmployeeRepository;
import com.mkoper.payroll.repository.RoleRepository;
import com.mkoper.payroll.repository.UserRepository;
import com.mkoper.payroll.security.JwtGenerator;
import com.mkoper.payroll.service.UserService;

@RestController
@RequestMapping("auth")
public class AuthController {
    
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private JwtGenerator jwtGenerator;

    @Autowired private UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository, JwtGenerator jwtGenerator, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.jwtGenerator = jwtGenerator;

        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        
        return new ResponseEntity<>(new AuthResponseDto(token, userService.getUserByUsername(loginDto.getUsername())), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setEmployee(employeeRepository.findById(registerDto.getId()).get());
        user.setUsername(registerDto.getUsername());
        // user.setPassword(registerDto.getPassword());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        if (registerDto.getRole() == null) {
            user.setRole(Collections.singletonList(roleRepository.findByName("USER").get()));
        }

        else {
            List<Role> roles = new ArrayList<>();

            registerDto.getRole().forEach(role -> {
                switch (role.toLowerCase()) {
                    case "admin":
                        Role adminRole = roleRepository.findByName("ADMIN").get();
                        roles.add(adminRole);

                        break;

                    case "moderator":
                        Role moderatorRole = roleRepository.findByName("MODERATOR").get();
                        roles.add(moderatorRole);

                        break;
                
                    default:
                        Role userRole = roleRepository.findByName("USER").get();
                        roles.add(userRole);

                        break;
                }
            });

            user.setRole(roles);
        }

        userRepository.save(user);

        return new ResponseEntity<>("User registration was successful", HttpStatus.OK);
    }
}
