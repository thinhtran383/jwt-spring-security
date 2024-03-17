package com.example.jwtspringboot.controllers;

import com.example.jwtspringboot.components.JwtUtils;
import com.example.jwtspringboot.models.User;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class LoginController {
    private final JwtUtils jwtUtils;
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody User user){
        System.out.println(user.getUsername());
        User loginUser = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .userRoles(new HashSet<>())
                .build();
        return ResponseEntity.ok(jwtUtils.generateToken(loginUser));
    }
}
