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

                // Allow GET requests to view public items (e.g., item listings on explore page)
                .requestMatchers(HttpMethod.GET, "/api/givenget/items/**")
                .permitAll()

                // Require token with `givenget:write` scope to POST (create) donation items
                .requestMatchers(HttpMethod.POST, "/api/givenget/items/**")
                .hasAuthority("SCOPE_givenget:write")

                // Require token for updating and deleting items
                .requestMatchers(HttpMethod.PUT, "/api/givenget/items/**")
                .hasAuthority("SCOPE_givenget:write")
                .requestMatchers(HttpMethod.DELETE, "/api/givenget/items/**")
                .hasAuthority("SCOPE_givenget:write")

                // Allow user registration and login endpoints (auth server handles this ideally)
                .requestMatchers("/api/givenget/auth/**")
                .permitAll()

                // Protect all other routes (e.g., user profiles, dashboard)
                .anyRequest()
                .authenticated()
            )

            // Use JWT tokens from the external Authorization Server (port 9000)
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

            .build();
    }
}