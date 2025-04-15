package com.example.givenget.service;

import com.example.givenget.model.*;
import com.example.givenget.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Key jwtKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Or externalize this for reuse

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> registerUser(SignupRequest req) {
        if (userRepository.findByEmail(req.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User newUser = new User(
            null,
            req.name(),
            req.phoneNum(),
            req.email(),
            req.location(),
            0,
            List.of(),
            List.of(),
            List.of(),
            List.of(),
            LocalDateTime.now()
        );

        // Save user with encoded password stored in a map or a separate auth store
        userRepository.save(newUser);
        userRepository.storePassword(req.email(), passwordEncoder.encode(req.password())); 

        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> authenticateUser(LoginRequest req) {
        Optional<User> userOpt = userRepository.findByEmail(req.email());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String encodedPassword = userRepository.getPasswordByEmail(req.email());
        if (!passwordEncoder.matches(req.password(), encodedPassword)) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = Jwts.builder()
            .setSubject(req.email())
            .claim("scope", "givenget:read givenget:write")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
            .signWith(jwtKey)
            .compact();

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
