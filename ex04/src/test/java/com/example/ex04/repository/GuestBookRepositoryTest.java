package com.example.ex04.repository;

import com.example.ex04.entity.GuestBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class GuestBookRepositoryTest {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void insertDummies() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder()
                    .title("Title...." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestBookRepository.save(guestBook));
        });
    }

    @Test
    @Transactional
    public void updateTest() {
        //given
        Long guestBookId = 300L;

        //when
        GuestBook result = guestBookRepository.findById(guestBookId)
                .orElseThrow(() -> new EntityNotFoundException("해당 GuestBook이 존재하지 않습니다."));

        LocalDateTime prevModifiedTime = result.getModDate();

        result.updateTitle("Changed Title....");
        result.updateContent("Changed Content....");
        guestBookRepository.flush();

        //then
        assertEquals(result.getTitle(), "Changed Title....");
        assertEquals(result.getContent(), "Changed Content....");
        assertNotEquals(prevModifiedTime, result.getModDate());

    }
}