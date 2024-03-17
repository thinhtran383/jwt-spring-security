package com.example.jwtspringboot.components;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwtspringboot.models.User;
import com.example.jwtspringboot.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${jwt.secretKey}")
    private String secretKey;
    private final UserRepository userRepository;

    public String generateToken(User user) {

        User foundUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Cannot found username: " + user.getUsername()));

        List<String> roles = foundUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = JWT.create()
                .withSubject(foundUser.getUsername())
                .withClaim("roles", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + 50*60 * 1000L))
                .sign(Algorithm.HMAC256(secretKey.getBytes()));

        System.out.println("token: " + extractUsername(token));
        System.out.println("claims:" + extractClaims(token));
        return token;
    }

    public String extractUsername(String token){
        DecodedJWT decodedJWT = getDecodedJWT(token);

        return decodedJWT.getSubject();
    }

    private DecodedJWT getDecodedJWT(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secretKey.getBytes())).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT;
    }

    public Claim extractClaims(String token){
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getClaim("roles");
    }


}
