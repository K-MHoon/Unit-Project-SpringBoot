package com.example.ex04.repository;

import com.example.ex04.entity.GuestBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long>, QuerydslPredicateExecutor<GuestBook> {

    @Query("select g from GuestBook g join fetch g.writer gw where g.id =:id")
    Optional<GuestBook> getGuestBookWithWriter(@Param("id") Long id);

    @Query("select distinct g from GuestBook g join fetch g.replyList gl where g.id = :id")
    Optional<GuestBook> getGuestBookWithReply(@Param("id") Long id);

    @Query(value = "select g, w, count(r) " +
            "from GuestBook g " +
            "left join g.writer w " +
            "left join g.replyList r " +
            "group by g",
            countQuery = "select count(g) from GuestBook g")
    Page<Object[]> getGuestBookWithWriterAndReply(Pageable pageable);

    @Query(value = "select g, w, count(r) " +
            "from GuestBook g " +
            "left join g.writer w " +
            "left join g.replyList r " +
            "where g.id = :id")
    Object getGuestBookById(@Param("id") Long id);
}
