package com.example.jwtspringboot.repositories;

import com.example.jwtspringboot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);


}