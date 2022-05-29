package com.example.ex05.repository;

import com.example.ex05.entity.member.Member;
import com.example.ex05.entity.review.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @Transactional
    @DisplayName("초기 회원 데이터 입력")
    @Rollback(value = false)
    public void insertMembers() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("r" + i + "kmhoon.com")
                    .pw("1111")
                    .nickname("reviewer" + i).build();
            memberRepository.save(member);
        });
    }

    @Test
    @DisplayName("회원 - 리뷰 삭제")
    @Transactional
    @Commit
    public void testDeleteMember() {
        Long mid = 69L;

        Member member = Member.builder().id(mid).build();

        // 리뷰 먼저 삭제되어야 한다.
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);

    }
}