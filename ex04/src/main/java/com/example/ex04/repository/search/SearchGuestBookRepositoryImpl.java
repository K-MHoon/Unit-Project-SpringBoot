package com.example.ex04.repository.search;

import com.example.ex04.entity.GuestBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Slf4j
public class SearchGuestBookRepositoryImpl extends QuerydslRepositorySupport implements SearchGuestBookRepository{

    public SearchGuestBookRepositoryImpl() {
        super(GuestBook.class);
    }

    @Override
    public GuestBook search1() {

        log.info("search1 ......");

        return null;
    }
}
