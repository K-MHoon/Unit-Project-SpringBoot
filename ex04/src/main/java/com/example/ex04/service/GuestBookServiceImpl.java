package com.example.ex04.service;

import com.example.ex04.dto.GuestBookDTO;
import com.example.ex04.entity.GuestBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GuestBookServiceImpl implements GuestBookService {

    @Override
    public Long register(GuestBookDTO dto) {

        GuestBook entity = dtoToEntity(dto);

        log.info("entity = {}", entity);

        return null;
    }
}
