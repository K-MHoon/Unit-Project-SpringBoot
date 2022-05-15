package com.example.ex04.service;

import com.example.ex04.dto.GuestBookDTO;
import com.example.ex04.dto.PageRequestDTO;
import com.example.ex04.dto.PageResultDTO;
import com.example.ex04.entity.GuestBook;
import com.example.ex04.entity.QGuestBook;
import com.example.ex04.repository.GuestBookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;
import java.util.function.Function;

import static com.example.ex04.entity.QGuestBook.guestBook;
import static org.thymeleaf.util.StringUtils.isEmpty;

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

    @Override
    @Transactional(readOnly = true)
    public PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Page<GuestBook> result = repository.findAll(booleanBuilder, pageable);
        Function<GuestBook, GuestBookDTO> fn =  (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = guestBook.id.gt(0L);
        booleanBuilder.and(expression);
        if(isEmpty(type)) {
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("t")) {
            conditionBuilder.or(guestBook.title.contains(keyword));
        }
        if(type.contains("c")) {
            conditionBuilder.or(guestBook.content.contains(keyword));
        }
        if(type.contains("w")) {
            conditionBuilder.or(guestBook.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }

    @Override
    @Transactional(readOnly = true)
    public GuestBookDTO read(Long id) {
        Optional<GuestBook> result = repository.findById(id);
        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void modify(GuestBookDTO dto) {
        Optional<GuestBook> result = repository.findById(dto.getId());

        if(result.isPresent()) {
            GuestBook entity = result.get();

            entity.updateTitle(dto.getTitle());
            entity.updateContent(dto.getContent());

            repository.save(entity);
        }
    }


}