package com.example.ex04.service;

import com.example.ex04.dto.GuestBookDTO;
import com.example.ex04.entity.GuestBook;

public interface GuestBookService {
    Long register(GuestBookDTO dto);

    default GuestBook dtoToEntity(GuestBookDTO dto) {
        GuestBook entity = GuestBook.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }
}
