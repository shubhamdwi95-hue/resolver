package com.resolver.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.resolver.entity.RefreshToken;
import com.resolver.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {
   
   private final RefreshTokenRepository repository; 
   
   public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

   public RefreshToken createRefreshToken(String username) {
    RefreshToken token = new RefreshToken();
    token.setUsername(username);
    token.setToken(UUID.randomUUID().toString());
    token.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60)); // 7 days
    return repository.save(token);
   }


}
