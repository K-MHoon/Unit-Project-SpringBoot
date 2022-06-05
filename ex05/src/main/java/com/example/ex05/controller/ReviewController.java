package com.example.ex05.controller;

import com.example.ex05.dto.ReviewDTO;
import com.example.ex05.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable Long mno) {
        log.info("[getList] mno = {}", mno);
        List<ReviewDTO> listOfMovie = reviewService.getListOfMovie(mno);
        return new ResponseEntity<>(listOfMovie, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> createReview(@PathVariable Long mno, @RequestBody ReviewDTO movieReviewDTO) {
        log.info("[create] reviewDTO : {}" + movieReviewDTO);

        Long reviewnum = reviewService.register(movieReviewDTO);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long mno, @PathVariable Long reviewnum, @RequestBody ReviewDTO movieReviewDTO) {
        log.info("[modify] reviewDTO : {}", movieReviewDTO);

        reviewService.modify(movieReviewDTO);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview(@PathVariable Long mno, @PathVariable Long reviewnum) {
        log.info("[remove] reviewNum : {}", reviewnum);

        reviewService.remove(reviewnum);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }
}
