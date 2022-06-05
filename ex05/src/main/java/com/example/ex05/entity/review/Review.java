package com.example.ex05.entity.review;

import com.example.ex05.entity.BaseEntity;
import com.example.ex05.entity.member.Member;
import com.example.ex05.entity.movie.Movie;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"movie", "member"})
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int grade;

    private String text;

    public void updateGrade(int grade) {
        this.grade = grade;
    }

    public void updateText(String text) {
        this.text = text;
    }
}
