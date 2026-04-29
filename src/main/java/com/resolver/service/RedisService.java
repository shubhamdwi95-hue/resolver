package com.resolver.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // SAVE with TTL
    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, 10, TimeUnit.MINUTES);
    }

    // GET
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // DELETE
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}