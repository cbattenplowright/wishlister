package com.caldev.wishlister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class WishlisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishlisterApplication.class, args);

	}
}
