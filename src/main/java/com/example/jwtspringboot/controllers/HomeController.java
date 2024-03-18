package com.example.jwtspringboot.controllers;

import com.example.jwtspringboot.components.JwtUtils;
import com.example.jwtspringboot.models.User;
import com.example.jwtspringboot.repositories.UserRepository;
import com.example.jwtspringboot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/home")
public class HomeController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @GetMapping
    public String hello(@RequestBody String token) {
        return "Hello with ROLE_ADMIN";
    }
    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello from spring";
    }
}
