package com.example.ex04.repository.search;

import com.example.ex04.entity.GuestBook;
import com.example.ex04.entity.QGuestBook;
import com.example.ex04.entity.QMember;
import com.example.ex04.entity.QReply;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.example.ex04.entity.QGuestBook.guestBook;
import static com.example.ex04.entity.QMember.member;
import static com.example.ex04.entity.QReply.reply;


@Slf4j
public class SearchGuestBookRepositoryImpl extends QuerydslRepositorySupport implements SearchGuestBookRepository{

    public SearchGuestBookRepositoryImpl() {
        super(GuestBook.class);
    }

    @Override
    public GuestBook search1() {

        log.info("search1 ......");

        JPQLQuery<GuestBook> jpqlQuery = from(guestBook);

        jpqlQuery.leftJoin(member).on(guestBook.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.guestBook.eq(guestBook));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(guestBook, member.email, reply.count()).groupBy(guestBook);

        log.info("tuple = {}", tuple);

        List<Tuple> result = tuple.fetch();

        log.info("result = {}", result);

        return null;
    }
}
