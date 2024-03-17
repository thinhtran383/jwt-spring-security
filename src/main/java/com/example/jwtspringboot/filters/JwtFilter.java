package com.example.jwtspringboot.filters;

import com.example.jwtspringboot.components.JwtUtils;
import com.example.jwtspringboot.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private final JwtUtils jwtUtils;
    @Autowired
    private final UserDetailsService userDetailsService;
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        filterChain.doFilter(request,response);
//
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            System.out.println("run");
            String token = authorizationHeader.substring(7);
            String username = jwtUtils.extractUsername(token);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Arrays.stream(jwtUtils.extractClaims(token).asArray(String.class)).forEach(role ->{
                authorities.add(new SimpleGrantedAuthority(role));
            });

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                User user = (User) userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request,response);
            }
        } else{
            filterChain.doFilter(request,response);
        }
    }



}
