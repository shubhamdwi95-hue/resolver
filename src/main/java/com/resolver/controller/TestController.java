package com.resolver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/")
    public String home()
    {
      return "Resolver API is working fine";
    }
    @GetMapping("/hello")
    public String hello()
    {
      return "Hello from resolver API";
    }

}
