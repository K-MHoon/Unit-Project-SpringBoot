package com.example.ex04.repository.search;

import com.example.ex04.entity.GuestBook;
import com.example.ex04.entity.QGuestBook;
import com.example.ex04.entity.QMember;
import com.example.ex04.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        JPQLQuery<GuestBook> jpqlQuery = from(guestBook);
        jpqlQuery.leftJoin(member).on(guestBook.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.guestBook.eq(guestBook));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(guestBook, member, reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = guestBook.id.gt(0L);

        booleanBuilder.and(expression);

        if(type != null) {
            String[] typeArr = type.split("");

            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        conditionBuilder.or(guestBook.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(guestBook.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);

        // sorting
        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(GuestBook.class, "guestBook");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(guestBook);

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info("result = {}", result);

        long count = tuple.fetchCount();

        log.info("count = {}", count);

        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,
                count
        );
    }
}
