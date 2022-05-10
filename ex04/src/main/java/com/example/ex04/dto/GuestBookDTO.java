package com.example.ex04.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GuestBookDTO {

    private Long id;
    private String title;
    private String content;
    public String writer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
