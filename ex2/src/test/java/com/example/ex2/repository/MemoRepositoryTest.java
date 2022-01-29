package com.example.ex2.repository;

import com.example.ex2.entity.Memo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.as;
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

    @BeforeEach
    void beforeEach() {
        // 100개의 데이터를 테스트 전에 insert
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample... " + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    @DisplayName("정상적으로 저장됐는지 확인")
    void testInsertCount() {
        assertThat(memoRepository.count()).isEqualTo(100);
    }

    @Test
    @DisplayName("페이징 처리 확인")
    void testPageDefault() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        assertThat(result.getTotalPages()).isEqualTo(10);
        assertThat(result.getTotalElements()).isEqualTo(100L);
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.hasNext()).isTrue();
        assertThat(result.isFirst()).isTrue();
    }

    @Test
    @DisplayName("페이징 정렬 확인")
    void testSort() {
        Sort sort1 = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort1);
        Page<Memo> result = memoRepository.findAll(pageable);
        assertThat(result.getContent().get(0).getMno()).isEqualTo(100L);
    }

    @Test
    @DisplayName("쿼리 메서드 테스트")
    void testQueryMethods() {
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
        assertThat(list.size()).isEqualTo(11);
        assertThat(list.get(0).getMno()).isEqualTo(80L);
        assertThat(list.get(10).getMno()).isEqualTo(70L);
    }

    @Test
    @DisplayName("JPA Delete 테스트")
    @Transactional
    void testDeleteQueryMethods() {
        int deletedMemoCount = memoRepository.deleteByMnoLessThan(10L);
        assertThat(deletedMemoCount).isEqualTo(9);
    }

    @Test
    @DisplayName("@Query 테스트")
    void testSimpleQueryMethod() {
        List<Memo> result = memoRepository.getListDesc();
        assertThat(result.get(0).getMno()).isEqualTo(100L);
    }
}