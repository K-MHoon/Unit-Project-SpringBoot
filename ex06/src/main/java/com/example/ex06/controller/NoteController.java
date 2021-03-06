package com.example.ex06.controller;

import com.example.ex06.dto.note.NoteDTO;
import com.example.ex06.service.note.NoteService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping(value = "")
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO) {

        log.info("noteDTO = {}", noteDTO);
        Long num = noteService.register(noteDTO);

        return new ResponseEntity<>(num, HttpStatus.OK);
    }

    @GetMapping(value = "/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteDTO> read(@PathVariable("num") Long num) {
        log.info("read num = {}", num);
        return new ResponseEntity<>(noteService.get(num), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NoteDTO>> getList(String email) {
        log.info("getList email = {}", email);
        return new ResponseEntity<>(noteService.getAllWithWriter(email), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable("num") Long num) {
        log.info("delete Num = {}", num);
        noteService.remove(num);
        return new ResponseEntity<>("removed", HttpStatus.OK);
    }

    @PutMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@RequestBody NoteDTO noteDTO) {
        log.info("modify noteDTO = {}", noteDTO);
        noteService.modify(noteDTO);
        return new ResponseEntity<>("modified", HttpStatus.OK);
    }
}
