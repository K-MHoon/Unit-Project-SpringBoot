package com.example.ex06.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("PasswordEncoder 동작을 확인한다.")
    public void testEncode() {
        String password = "1111";

        String enPw = passwordEncoder.encode(password);
        System.out.println(enPw);

        boolean matches = passwordEncoder.matches(password, enPw);
        System.out.println(matches);
    }
}