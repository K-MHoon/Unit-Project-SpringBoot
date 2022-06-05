package com.example.ex05.service.review;

import com.example.ex05.dto.ReviewDTO;
import com.example.ex05.entity.member.Member;
import com.example.ex05.entity.movie.Movie;
import com.example.ex05.entity.review.Review;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getListOfMovie(Long mno);

    Long register(ReviewDTO movieReviewDTO);

    void modify(ReviewDTO movieReviewDTO);

    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO movieReviewDTO) {
        return Review.builder()
                .reviewnum(movieReviewDTO.getReviewnum())
                .movie(Movie.builder().mno(movieReviewDTO.getMno()).build())
                .member(Member.builder().id(movieReviewDTO.getMid()).build())
                .grade(movieReviewDTO.getGrade())
                .text(movieReviewDTO.getText())
                .build();
    }

    default ReviewDTO entityToDto(Review movieReview) {
        return ReviewDTO.builder()
                .reviewnum(movieReview.getReviewnum())
                .mno(movieReview.getMovie().getMno())
                .mid(movieReview.getMember().getId())
                .nickName(movieReview.getMember().getNickname())
                .email(movieReview.getMember().getEmail())
                .grade(movieReview.getGrade())
                .text(movieReview.getText())
                .regDate(movieReview.getRegDate())
                .modDate(movieReview.getModDate())
                .build();
    }
}
