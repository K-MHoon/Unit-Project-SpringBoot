package com.example.ex04.repository.search;

import com.example.ex04.entity.GuestBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchGuestBookRepository {
    GuestBook search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);


}
