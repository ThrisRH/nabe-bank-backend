package com.myproject.nabe_bank.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.myproject.nabe_bank.core.security.jwt.JwtAuthenticationFilter;
import com.myproject.nabe_bank.core.security.jwt.JwtProvider;

@Configuration
public class SecurityConfig {
    private final JwtProvider jwtProvider;

    public SecurityConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}
