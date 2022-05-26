package com.example.ex04.repository;

import com.example.ex04.entity.GuestBook;
import com.example.ex04.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query("delete from Reply r where r.guestBook.id = :bno")
    void deleteByBno(@Param("bno") Long bno);

    List<Reply> getRepliesByGuestBookOrderById(GuestBook guestBook);
}
