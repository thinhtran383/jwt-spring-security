package com.example.jwtspringboot.controllers;

import com.example.jwtspringboot.components.JwtUtils;
import com.example.jwtspringboot.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping("${api.prefix}/auth/")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody User user) {
        log.info("login: " + user);
        User loginUser = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .userRoles(new HashSet<>())
                .build();
        return ResponseEntity.ok(jwtUtils.generateToken(loginUser));
    }
}
