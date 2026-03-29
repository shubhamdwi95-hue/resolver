package com.resolver.config;

import com.resolver.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

           .authorizeHttpRequests(auth -> auth
        .requestMatchers(
                "/auth/login",
                "/auth/refresh",
                "/swagger-ui/**",
                "/v3/api-docs/**"
        ).permitAll()
        .requestMatchers("/tickets/**").hasAnyRole("ADMIN","USER")
        .anyRequest().authenticated()
)

            .addFilterBefore(new JwtAuthenticationFilter(),
                    UsernamePasswordAuthenticationFilter.class);

            

        return http.build();
    }
}