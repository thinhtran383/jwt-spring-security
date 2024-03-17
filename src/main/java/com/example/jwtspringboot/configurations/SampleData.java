package com.example.jwtspringboot.configurations;

import com.example.jwtspringboot.models.User;
import com.example.jwtspringboot.repositories.RoleRepository;
import com.example.jwtspringboot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SampleData {
    private final UserService userService;

    @Bean
    public CommandLineRunner runner(){
        return args -> {
//            userService.addRole("ADMIN");
//            userService.addRole("STAFF");
//
//            userService.addUser("thinhtran383","123","ADMIN");
//            userService.addUser("vanhlu383","123","STAFF");
        };
    }
}
