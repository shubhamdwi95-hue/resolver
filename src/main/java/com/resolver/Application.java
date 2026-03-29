 package com.resolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
         System.out.println(encoder.encode("user123"));
       SpringApplication.run(Application.class, args);
		
	}

}
