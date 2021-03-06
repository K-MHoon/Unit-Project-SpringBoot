package com.example.ex04.service;

import com.example.ex04.dto.GuestBookDTO;
import com.example.ex04.dto.PageRequestDTO;
import com.example.ex04.dto.PageResultDTO;
import com.example.ex04.entity.GuestBook;
import com.example.ex04.entity.Member;

public interface GuestBookService {
    Long register(GuestBookDTO dto);

    PageResultDTO<GuestBookDTO, Object[]> getList(PageRequestDTO requestDTO);

    default GuestBookDTO entityToDto(GuestBook entity, Member member, Long replyCount) {
        return GuestBookDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
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
    void removeWithReplies(Long id);
    void modify(GuestBookDTO dto);
}
