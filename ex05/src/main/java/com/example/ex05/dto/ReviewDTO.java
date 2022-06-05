package com.example.ex05.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewDTO {

    private Long reviewnum;

    private Long mno;

    private Long mid;

    private String nickName;

    private String email;

    private Integer grade;

    private String text;

    private LocalDateTime regDate, modDate;
}
