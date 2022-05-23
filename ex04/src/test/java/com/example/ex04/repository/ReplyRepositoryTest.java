package com.example.ex04.repository;

import com.example.ex04.entity.GuestBook;
import com.example.ex04.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTest {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void insertReply() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Long id = (long)(Math.random() * 100) + 1;

            GuestBook guestBook = GuestBook.builder().id(id).build();

            Reply reply = Reply.builder()
                    .text("Reply........." + i)
                    .guestBook(guestBook)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);

        });
    }

    @Test
    public void testListByGuestBook() {
        List<Reply> replyList = replyRepository.getRepliesByGuestBookOrderById(GuestBook.builder().id(95L).build());

        replyList.forEach(reply -> System.out.println(reply));
    }
}