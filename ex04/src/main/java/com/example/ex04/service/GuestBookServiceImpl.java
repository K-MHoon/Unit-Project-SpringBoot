package com.example.ex04.service;

import com.example.ex04.dto.GuestBookDTO;
import com.example.ex04.dto.PageRequestDTO;
import com.example.ex04.dto.PageResultDTO;
import com.example.ex04.entity.GuestBook;
import com.example.ex04.entity.Member;
import com.example.ex04.repository.GuestBookRepository;
import com.example.ex04.repository.ReplyRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

import static com.example.ex04.entity.QGuestBook.guestBook;
import static org.thymeleaf.util.StringUtils.isEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository guestBookRepository;
    private final ReplyRepository replyRepository;

    @Override
    @Transactional
    public Long register(GuestBookDTO dto) {

        GuestBook entity = dtoToEntity(dto);

        log.info("{}", entity);

        guestBookRepository.save(entity);

        return entity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResultDTO<GuestBookDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Function<Object[], GuestBookDTO> fn =  (entity -> entityToDto((GuestBook)entity[0],
                (Member)entity[1], (Long)entity[2]));

//        Page<Object[]> result = guestBookRepository.getGuestBookWithWriterAndReply(pageable);
        Page<Object[]> result = guestBookRepository.searchPage(requestDTO.getType(),
                requestDTO.getKeyword(),
                requestDTO.getPageable(Sort.by("id").descending()));

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
            conditionBuilder.or(guestBook.writer.name.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }

    @Override
    @Transactional(readOnly = true)
    public GuestBookDTO read(Long id) {
        Object result = guestBookRepository.getGuestBookById(id);
        Object[] arr = (Object[]) result;
        return entityToDto((GuestBook)arr[0], (Member)arr[1], (Long)arr[2]);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        guestBookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void removeWithReplies(Long guestBookId) {
        replyRepository.deleteById(guestBookId);
        guestBookRepository.deleteById(guestBookId);
    }

    @Override
    @Transactional
    public void modify(GuestBookDTO dto) {
        Optional<GuestBook> result = guestBookRepository.findById(dto.getId());

        if(result.isPresent()) {
            GuestBook entity = result.get();

            entity.updateTitle(dto.getTitle());
            entity.updateContent(dto.getContent());

            guestBookRepository.save(entity);
        }
    }


}