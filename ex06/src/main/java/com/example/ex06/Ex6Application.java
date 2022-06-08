package com.example.ex06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class Ex6Application {

    public static void main(String[] args) {
        SpringApplication.run(Ex6Application.class, args);
    }
}
