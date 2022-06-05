package com.example.ex05.service.review;

import com.example.ex05.dto.ReviewDTO;
import com.example.ex05.entity.movie.Movie;
import com.example.ex05.entity.review.Review;
import com.example.ex05.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> getListOfMovie(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        return result.stream().map(movieReview -> entityToDto(movieReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO movieReviewDTO) {
        Review movieReview = dtoToEntity(movieReviewDTO);

        reviewRepository.save(movieReview);

        return movieReview.getReviewnum();
    }

    @Override
    public void modify(ReviewDTO movieReviewDTO) {
        Review result = reviewRepository.findById(movieReviewDTO.getReviewnum())
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰가 존재하지 않습니다."));

        result.updateGrade(movieReviewDTO.getGrade());
        result.updateText(movieReviewDTO.getText());

        reviewRepository.save(result);
    }

    @Override
    public void remove(Long reviewnum) {
        reviewRepository.deleteById(reviewnum);
    }
}
