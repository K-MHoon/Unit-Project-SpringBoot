package com.example.ex06.entity.club;

import com.example.ex06.enums.club.ClubMemberRole;
import com.example.ex06.repository.club.ClubMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClubMemberTest {

    @Autowired
    private ClubMemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {

        IntStream.rangeClosed(1,100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user" + i + "@kmhoon.com")
                    .name("사용자" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();
            clubMember.addMemberRole(ClubMemberRole.USER);
            if(i > 80) {
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }

            if(i > 90) {
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }

            repository.save(clubMember);
        });
    }

    @Test
    public void findClubMemberGetClubMember() {
        ClubMember clubMember = repository.findByEmail("user95@kmhoon.com", false)
                .orElseThrow(() -> new EntityNotFoundException("해당 ClubMember가 존재하지 않습니다."));

        System.out.println(clubMember);
    }
}