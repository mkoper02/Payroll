package com.mkoper.payroll.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.AuthResponseDto;
import com.mkoper.payroll.dto.LoginDto;
import com.mkoper.payroll.dto.RegisterDto;
import com.mkoper.payroll.model.Employee;
import com.mkoper.payroll.model.Role;
import com.mkoper.payroll.model.UserEntity;
import com.mkoper.payroll.repository.EmployeeRepository;
import com.mkoper.payroll.repository.RoleRepository;
import com.mkoper.payroll.repository.UserRepository;
import com.mkoper.payroll.security.JwtGenerator;
import com.mkoper.payroll.service.UserService;

@RestController
@RequestMapping("auth")
@CrossOrigin
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

    @GetMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        
        return new ResponseEntity<>(new AuthResponseDto(token, userService.getUserByUsername(loginDto.getUsername())), HttpStatus.OK);
    }

    @PostMapping("register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsById(registerDto.getId())) {
            return new ResponseEntity<>("User with given ID already exists!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        Employee employee = employeeRepository.findById(registerDto.getId()).get();

        user.setEmployee(employee);

        if (registerDto.getUsername() == null && registerDto.getPassword() == null) {
            String usernamePassword = removePolishSigns(employee.getFirstName().substring(0, 3) + employee.getLastName().substring(0, 3) + employee.getId().toString().toLowerCase());

            user.setUsername(usernamePassword);
            user.setPassword(passwordEncoder.encode(usernamePassword));
        }

        else {
            user.setUsername(registerDto.getUsername());
            // user.setPassword(registerDto.getPassword());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        }

        if (registerDto.getRole() == null) {
            user.setRole(Collections.singletonList(roleRepository.findByName("USER").get()));
        }

        else {
            List<Role> roles = new ArrayList<>();

            registerDto.getRole().forEach(role -> {
                switch (role.toLowerCase()) {
                    case "admin":
                        roles.add(roleRepository.findByName("ADMIN").get());
                        break;

                    // case "moderator":
                    //     roles.add(roleRepository.findByName("MODERATOR").get());
                    //     break;
                
                    default:
                        roles.add(roleRepository.findByName("USER").get());
                        break;
                }
            });

            user.setRole(roles);
        }

        userRepository.save(user);

        return new ResponseEntity<>("User registration was successful", HttpStatus.OK);
    }

    private static String removePolishSigns(String word){

        String[] polish = {"Ą", "ą", "Ć", "ć", "Ę", "ę", "Ł", "ł","Ń", "ń", "Ó", "ó", "Ś", "ś", "Ź", "ź", "Ż", "ż"};
        String[] standard = {"A", "a", "C", "c", "E", "e", "L", "l","N", "n", "O", "o", "S", "s", "Z", "z", "Z", "z"};

        StringBuilder result = new StringBuilder();
        for (char ch : word.toCharArray()) {
            int index = -1;
            for (int i = 0; i< polish.length; i++) {
                if (polish[i].charAt(0) == ch) {
                    index = i;
                    break;
                }
            }

            if (index >= 0) {
                result.append(standard[index]);
            }
            
            else {
                result.append(ch);
            }
        }

        return result.toString();
    }
}
