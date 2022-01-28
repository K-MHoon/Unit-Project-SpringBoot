package com.example.ex2.repository;

import com.example.ex2.entity.Memo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    @DisplayName("의존성 주입에 문제 없는지 테스트")
    void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    @DisplayName("100개의 데이터를 저장하고 갯수 확인")
    void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample... " + i).build();
            memoRepository.save(memo);
        });

        assertThat(memoRepository.count()).isEqualTo(100);
    }
}