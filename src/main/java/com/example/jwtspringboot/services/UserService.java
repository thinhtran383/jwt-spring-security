package com.example.jwtspringboot.services;

import com.example.jwtspringboot.models.Role;
import com.example.jwtspringboot.models.User;
import com.example.jwtspringboot.models.UserRole;
import com.example.jwtspringboot.repositories.RoleRepository;
import com.example.jwtspringboot.repositories.UserRepository;
import com.example.jwtspringboot.repositories.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRoleRepository userRoleRepository;

    public void addUser(String username, String password, String roleName) {
        User newUser = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .userRoles(new HashSet<>())
                .build();

        Role role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role not exists: " + roleName));

        UserRole userRole = UserRole.builder()
                .user(newUser)
                .role(role)
                .build();

        newUser.getUserRoles().add(userRole);
        userRepository.save(newUser);
        userRoleRepository.save(userRole);
    }

    public void addRole(String roleName) {

        roleRepository.save(Role.builder()
                .roleName(roleName)
                .build());
    }
}
