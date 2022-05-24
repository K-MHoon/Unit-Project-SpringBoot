package com.example.ex04.controller;

import com.example.ex04.dto.ReplyDTO;
import com.example.ex04.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/replies")
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping(value = "/guestBook/{guestBookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByGuestBook(@PathVariable Long guestBookId) {
        log.info("guestBook ID = {}", guestBookId);
        return new ResponseEntity<>(replyService.getList(guestBookId), HttpStatus.OK);
    }
}
