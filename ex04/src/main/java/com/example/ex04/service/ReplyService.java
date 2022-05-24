package com.example.ex04.service;

import com.example.ex04.dto.ReplyDTO;
import com.example.ex04.entity.GuestBook;
import com.example.ex04.entity.Reply;

import java.util.List;

public interface ReplyService {
    
    Long register(ReplyDTO replyDTO);
    List<ReplyDTO> getList(Long guestBookId);

    default ReplyDTO entityToDto(Reply reply) {
        return ReplyDTO.builder()
                .id(reply.getId())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
    }

    default Reply dtoToEntity(ReplyDTO dto) {
        return Reply.builder()
                .id(dto.getId())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .guestBook(GuestBook.builder().id(dto.getGuestBookId()).build())
                .build();
    }
    
    void modify(ReplyDTO replyDTO);
    void remove(Long id);
}
