package com.mkoper.payroll.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.dto.UserDto;
import com.mkoper.payroll.exceptions.UserNotFoundException;
import com.mkoper.payroll.model.Role;
import com.mkoper.payroll.model.UserEntity;
import com.mkoper.payroll.repository.UserRepository;
import com.mkoper.payroll.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired 
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAll() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map((userEntity) -> mapToUserDto(userEntity)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) {
        UserEntity user = userRepository.findByEmployeeId(userId).orElseThrow(() -> new UserNotFoundException("User could not be found!"));
        return mapToUserDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User could not be found!"));
        return mapToUserDto(user);
    }
    
    private UserDto mapToUserDto(UserEntity user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());

        // TODO: check if deleting this line improve login behaviour
        userDto.setRole(user.getRole().stream().map(Role::getName).collect(Collectors.toList()));
        
        return userDto; 
    }
}
