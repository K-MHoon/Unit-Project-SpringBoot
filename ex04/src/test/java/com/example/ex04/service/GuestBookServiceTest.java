package com.example.ex04.service;

import com.example.ex04.dto.GuestBookDTO;
import com.example.ex04.dto.PageRequestDTO;
import com.example.ex04.dto.PageResultDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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