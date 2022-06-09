package com.example.ex06.repository.club;

import com.example.ex06.entity.club.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {
}
