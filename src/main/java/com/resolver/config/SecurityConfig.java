package com.resolver.config;

import com.resolver.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import com.resolver.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

 @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

           .authorizeHttpRequests(auth -> auth
        .requestMatchers(
                "/auth/login",
                "/auth/refresh",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/actuator/**",
                "/rabbit-test",
                "/tickets"
        ).permitAll()
        .requestMatchers("/tickets/**").permitAll()
        .anyRequest().authenticated()
)

            .addFilterBefore(jwtAuthenticationFilter(),
                    UsernamePasswordAuthenticationFilter.class);

            

        return http.build();
    }
}