package com.resolver.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.resolver.service.RefreshTokenService;
import com.resolver.entity.RefreshToken;
import com.resolver.entity.User;
import com.resolver.repository.UserRepository;
import com.resolver.security.JwtUtil;
import com.resolver.repository.RefreshTokenRepository;
import com.resolver.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserRepository userRespository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

     @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
private UserRepository userRepository;

@Autowired
private RefreshTokenRepository refreshTokenRepository;
  

// LOGIN API
    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username,
                                     @RequestParam String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(passwordEncoder.matches(password, user.getPassword())) {

            String accessToken = JwtUtil.generateToken(
                    user.getUsername(),
                    user.getRole()
            );

            RefreshToken refreshToken =
                    refreshTokenService.createRefreshToken(user.getUsername());

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken.getToken());

            return tokens;
        }

        throw new RuntimeException("Invalid credentials");
    }

@PostMapping("/refresh")
public Map<String, String> refresh(@RequestParam String refreshToken) {

    RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
            .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

    if(token.getExpiryDate().isBefore(Instant.now())) {
        throw new RuntimeException("Refresh token expired");
    }

    // fetch user to get role
    User user = userRepository.findByUsername(token.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

    String newAccessToken = JwtUtil.generateToken(
            user.getUsername(),
            user.getRole()
    );

    Map<String, String> response = new HashMap<>();
    response.put("accessToken", newAccessToken);

    return response;
}

}