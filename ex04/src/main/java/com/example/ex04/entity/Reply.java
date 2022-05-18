package com.example.ex04.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"guestBook"})
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guestbook_id")
    private GuestBook guestBook;
}
