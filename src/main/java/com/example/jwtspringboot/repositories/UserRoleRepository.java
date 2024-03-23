package com.example.jwtspringboot.repositories;

import com.example.jwtspringboot.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}