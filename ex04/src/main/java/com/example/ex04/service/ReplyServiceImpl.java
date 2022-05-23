package com.example.ex04.service;

import com.example.ex04.dto.ReplyDTO;
import com.example.ex04.entity.GuestBook;
import com.example.ex04.entity.Reply;
import com.example.ex04.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);

        replyRepository.save(reply);

        return reply.getId();
    }

    @Override
    public List<ReplyDTO> getList(Long id) {
        List<Reply> result = replyRepository.getRepliesByGuestBookOrderById(GuestBook.builder().id(id).build());
        return result.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long id) {
        replyRepository.deleteById(id);
    }
}
