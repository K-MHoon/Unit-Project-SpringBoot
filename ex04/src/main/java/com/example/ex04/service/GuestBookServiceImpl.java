package com.example.ex04.service;

import com.example.ex04.dto.GuestBookDTO;
import com.example.ex04.entity.GuestBook;
import com.example.ex04.repository.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository repository;

    @Override
    @Transactional
    public Long register(GuestBookDTO dto) {

        GuestBook entity = dtoToEntity(dto);

        log.info("{}", entity);

        repository.save(entity);

        return entity.getId();
    }
}
