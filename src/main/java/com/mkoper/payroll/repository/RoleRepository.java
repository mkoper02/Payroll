package com.mkoper.payroll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkoper.payroll.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    // Check if given role exist in the db
    Optional<Role> findByName(String name);
}
