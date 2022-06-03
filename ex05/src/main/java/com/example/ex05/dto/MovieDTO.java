package com.example.ex05.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long mno;

    private String title;

    @Builder.Default
    private List<MovieImageDTO> imageDTOList = new ArrayList<>();

    //영화의 평균 평점
    private Double avg;

    //리뷰 수
    private Long reviewCnt;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
