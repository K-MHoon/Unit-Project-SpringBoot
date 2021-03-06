package com.example.ex04.service;

import com.example.ex04.dto.GuestBookDTO;
import com.example.ex04.dto.PageRequestDTO;
import com.example.ex04.dto.PageResultDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                .writerEmail("user55@aaa.com")
                .build();

        System.out.println(service.register(bookDTO));
    }

    @Test
    public void testRemove() {
        // given
        Long id = 1L;

        // when
        service.removeWithReplies(id);
    }

    @Test
    public void testList() {
        //given
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        //when
        PageResultDTO<GuestBookDTO, Object[]> resultDTO = service.getList(pageRequestDTO);

        //then
        assertEquals(resultDTO.isPrev(), false);
        assertEquals(resultDTO.isNext(), true);
        assertEquals(resultDTO.getPage(), 1);
        assertEquals(resultDTO.getSize(), 10);

        //print
        for (GuestBookDTO guestBookDTO : resultDTO.getDtoList()) {
            System.out.println(guestBookDTO);
        }
        resultDTO.getPageList().forEach(System.out::print);
    }

    @Test
    public void testModify() {
        // given
        Long id = 2L;

        // when
        GuestBookDTO build = GuestBookDTO.builder()
                .id(id)
                .title("제목 변경합니다..")
                .content("내용 변경합니다..")
                .build();
        service.modify(build);
    }

    @Test
    public void testRead() {
        // given
        Long id = 100L;

        // when
        GuestBookDTO guestBookDTO = service.read(id);

        // then
        assertEquals(guestBookDTO.getId(), 100L);

        // print
        System.out.println(guestBookDTO);
    }

    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("한글")
                .build();

        PageResultDTO<GuestBookDTO, Object[]> resultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV : " + resultDTO.isPrev());
        System.out.println("NEXT : " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());
        for (GuestBookDTO guestBookDTO : resultDTO.getDtoList()) {
            System.out.println(guestBookDTO);
        }
    }
}