package com.mkoper.payroll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    // Search for user with given ID
    Optional<UserEntity> findByEmployeeId(Long id);

    // Search for user with given username
    Optional<UserEntity> findByUsername(String username);

    // Check if user with given username exist in the db
    Boolean existsByUsername(String username);
}