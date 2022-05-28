package com.example.ex05.repository;

import com.example.ex05.entity.member.Member;
import com.example.ex05.entity.movie.Movie;
import com.example.ex05.entity.review.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @Transactional
    @DisplayName("초기 영화-회원 매핑 데이터 입력")
    @Rollback(value = false)
    public void insertMovieReviews() {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            // 영화 번호
            Long mno = (long)(Math.random()*100) + 1;

            // 리뷰어 번호
            Long mid = (long)(Math.random()*100) + 1;
            Member member = Member.builder().id(mid).build();

            Review movieReview = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int) (Math.random() * 5) + 1)
                    .text("이 영화에 대한 느낌..." + i)
                    .build();

            reviewRepository.save(movieReview);
        });
    }
}