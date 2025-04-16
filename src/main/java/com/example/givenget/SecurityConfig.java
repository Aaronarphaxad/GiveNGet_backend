package com.example.givenget;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
        .authorizeHttpRequests(authorize -> authorize

            // Public access for signup & login
            .requestMatchers(HttpMethod.POST, "/api/givenget/auth/signup").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/givenget/auth/login").permitAll()

            // Public GET access to view donation items
            .requestMatchers(HttpMethod.GET, "/api/givenget/items/**").permitAll()

            // Protect item creation and updates
            .requestMatchers(HttpMethod.POST, "/api/givenget/items/**").hasAuthority("SCOPE_givenget:write")
            .requestMatchers(HttpMethod.PUT, "/api/givenget/items/**").hasAuthority("SCOPE_givenget:write")
            .requestMatchers(HttpMethod.DELETE, "/api/givenget/items/**").hasAuthority("SCOPE_givenget:delete")

            // Protect user resource access with scopes
            .requestMatchers(HttpMethod.GET, "/api/givenget/users/**").hasAuthority("SCOPE_givenget:read")
            .requestMatchers(HttpMethod.PUT, "/api/givenget/users/**").hasAuthority("SCOPE_givenget:write")
            .requestMatchers(HttpMethod.DELETE, "/api/givenget/users/**").hasAuthority("SCOPE_givenget:delete")

            // All other routes must be authenticated
            .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .build();
}
}