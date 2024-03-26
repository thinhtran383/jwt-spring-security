package com.example.jwtspringboot.controllers;

import com.example.jwtspringboot.components.JwtUtils;
import com.example.jwtspringboot.models.User;
import com.example.jwtspringboot.repositories.UserRepository;
import com.example.jwtspringboot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public String hello() {
        return "Hello with ROLE_ADMIN";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from spring";
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "1", required = false) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.getAllUsers(pageable);

        return ResponseEntity.ok(users.getContent());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUserPassword(user.getUsername(), user.getPassword()));
    }
}
