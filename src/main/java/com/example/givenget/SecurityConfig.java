package com.example.givenget;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

@Configuration
public class SecurityConfig {
    public static final SecretKey JWT_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ✅ Public access for signup & login
                        .requestMatchers("/api/givenget/auth/signup").permitAll()
                        .requestMatchers("/api/givenget/auth/login").permitAll()

                        // ✅ Public docs
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                        // ✅ Public read-only GET access to donation items
                        .requestMatchers(HttpMethod.GET, "/api/givenget/items/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/givenget/items/donor/**").permitAll()

                        // 🔐 Write/update/delete requires scope
                        .requestMatchers(HttpMethod.POST, "/api/givenget/items/**").hasAuthority("SCOPE_givenget:write")
                        .requestMatchers(HttpMethod.PUT, "/api/givenget/items/**").hasAuthority("SCOPE_givenget:write")
                        .requestMatchers(HttpMethod.DELETE, "/api/givenget/items/**").hasAuthority("SCOPE_givenget:delete")

                        // 🔐 Protect user profile endpoints
                        .requestMatchers(HttpMethod.GET, "/api/givenget/users/**").hasAuthority("SCOPE_givenget:read")
                        .requestMatchers(HttpMethod.PUT, "/api/givenget/users/**").hasAuthority("SCOPE_givenget:write")
                        .requestMatchers(HttpMethod.DELETE, "/api/givenget/users/**").hasAuthority("SCOPE_givenget:delete")

                        // All other routes must be authenticated
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(Customizer.withDefaults())
                )
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        System.out.println("✅ Custom JwtDecoder initialized!");
        return NimbusJwtDecoder.withSecretKey(SecurityConfig.JWT_KEY).build();
    }
}