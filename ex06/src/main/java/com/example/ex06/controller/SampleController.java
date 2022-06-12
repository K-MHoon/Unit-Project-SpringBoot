package com.example.ex06.controller;

import com.example.ex06.security.dto.ClubAuthMemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/sample/")
public class SampleController {

    // 로그인을 하지 않은 사용자도 접근 가능
    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public void exAll() {
        log.info("exAll");
    }

    // 로그인한 사용자만 접근 가능
    // @AuthenticationPrincipal은 별도의 캐스팅 작업 없이 실제 ClubAuthMemberDTO를 사용할 수 있다.
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        log.info("exMember");

        log.info("---------------------------");
        log.info("user = {}", clubAuthMemberDTO);
    }

    // @PreAuthorize의 value 표현식은 #과 같은 특별한 기호나 authentication 같은 내장 변수를 이용할 수 있다.
    @PreAuthorize("#clubAuthMember != null && #clubAuthMember.username eq \"user95@kmhoon.com\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember) {
        log.info("exMemberOnly");
        log.info("{}", clubAuthMember);
        return "/sample/admin";
    }


    // 관리자(admin)권한이 있는 사용자만 접근 가능
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin");
    }
}
