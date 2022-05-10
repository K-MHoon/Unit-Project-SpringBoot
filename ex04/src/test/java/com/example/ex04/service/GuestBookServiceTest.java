package com.example.ex04.service;

import com.example.ex04.dto.GuestBookDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestBookServiceTest {

    @Autowired
    private GuestBookService service;

    @Test
    @DisplayName("Register 메서드 정상작동 테스트")
    public void testRegister() {

        GuestBookDTO bookDTO = GuestBookDTO.builder()
                .title("Simple Title Test")
                .content("Simple Content Test")
                .writer("user0")
                .build();

        System.out.println(service.register(bookDTO));
    }
}