package com.example.ex06.controller;

import com.example.ex06.security.dto.ClubAuthMemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/sample/")
public class SampleController {

    // 로그인을 하지 않은 사용자도 접근 가능
    @GetMapping("/all")
    public void exAll() {
        log.info("exAll");
    }

    // 로그인한 사용자만 접근 가능
    // @AuthenticationPrincipal은 별도의 캐스팅 작업 없이 실제 ClubAuthMemberDTO를 사용할 수 있다.
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        log.info("exMember");

        log.info("---------------------------");
        log.info("user = {}", clubAuthMemberDTO);
    }

    // 관리자(admin)권한이 있는 사용자만 접근 가능
    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin");
    }
}
