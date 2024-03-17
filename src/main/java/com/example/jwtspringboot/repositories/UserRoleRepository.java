package com.example.jwtspringboot.repositories;

import com.example.jwtspringboot.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}