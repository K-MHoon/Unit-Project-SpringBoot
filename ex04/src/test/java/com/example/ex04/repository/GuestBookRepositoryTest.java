package com.example.ex04.repository;

import com.example.ex04.entity.GuestBook;
import com.example.ex04.entity.Member;
import com.example.ex04.entity.QGuestBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static com.example.ex04.entity.QGuestBook.*;
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

        IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder().email("user" + i + "@aaa.com").build();

            GuestBook guestBook = GuestBook.builder()
                    .title("Title...." + i)
                    .content("Content..." + i)
                    .writer(member)
                    .build();
            guestBookRepository.save(guestBook);
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

    @Test
    @DisplayName("페이징 처리 테스트")
    @Transactional(readOnly = true)
    public void testQuery1() {
        // given
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        QGuestBook qGuestBook = guestBook;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();

        //when
        BooleanExpression expression = qGuestBook.title.contains(keyword);
        builder.and(expression);
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        //then
        assertEquals(result.getContent().size(), 10);
    }

    @Test
    @DisplayName("제목과 내용 + id가 0보다 큰 경우 테스트")
    @Transactional(readOnly = true)
    public void testQuery2() {
        // given
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        QGuestBook qGuestBook = guestBook;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();

        //when
        BooleanExpression exTitle = qGuestBook.title.contains(keyword);
        BooleanExpression exContent = qGuestBook.content.contains(keyword);
        BooleanExpression exAll
                = exTitle.or(exContent);

        builder.and(exAll);
        builder.and(qGuestBook.id.gt(0L));

        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        //then
        result.forEach(gb -> System.out.println(gb));
        assertEquals(result.getContent().size(), 10);
    }



}