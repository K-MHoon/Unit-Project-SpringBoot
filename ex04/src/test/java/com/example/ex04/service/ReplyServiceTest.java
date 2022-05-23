package com.example.ex04.service;

import com.example.ex04.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyServiceTest {

    @Autowired
    ReplyService replyService;

    @Test
    public void testGetList() {
        Long id = 95L;

        List<ReplyDTO> replyDTOList = replyService.getList(id);

        replyDTOList.forEach(replyDTO -> System.out.println(replyDTO));
    }
}