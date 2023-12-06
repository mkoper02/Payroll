package com.mkoper.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Search for user with given username
    User findByUsername(String username);

    // Search for users with given access level
    List<User> findByAccessLevel(String accessLevel);

}