package com.example.hotelbao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class HotelbaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelbaoApplication.class, args);
	}

}