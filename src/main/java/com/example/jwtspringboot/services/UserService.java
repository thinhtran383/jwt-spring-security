package com.example.jwtspringboot.services;

import com.example.jwtspringboot.models.Role;
import com.example.jwtspringboot.models.User;
import com.example.jwtspringboot.models.UserRole;
import com.example.jwtspringboot.repositories.RoleRepository;
import com.example.jwtspringboot.repositories.UserRepository;
import com.example.jwtspringboot.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
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

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public boolean updateUserPassword(String username, String newPassword) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

    public boolean validateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);

        return user.filter(value -> passwordEncoder.matches(password, value.getPassword())).isPresent();
    }
}
