package com.example.ex06.entity.note;

import com.example.ex06.entity.club.ClubMember;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClubMember writer;

    private LocalDateTime regDate, modDate;

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

}
