package com.example.ex04.service;

import com.example.ex04.dto.GuestBookDTO;
import com.example.ex04.dto.PageRequestDTO;
import com.example.ex04.dto.PageResultDTO;
import com.example.ex04.entity.GuestBook;
import com.example.ex04.entity.Member;

public interface GuestBookService {
    Long register(GuestBookDTO dto);

    PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO);

    default GuestBookDTO entityToDto(GuestBook entity) {
        return GuestBookDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
//                .writer()
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }

    default GuestBook dtoToEntity(GuestBookDTO dto) {

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        return GuestBook.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
    }

    GuestBookDTO read(Long id);
    void remove(Long id);
    void modify(GuestBookDTO dto);
}
