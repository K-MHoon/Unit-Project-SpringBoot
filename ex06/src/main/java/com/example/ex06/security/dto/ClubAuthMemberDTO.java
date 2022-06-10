package com.example.ex06.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * DTO 역할을 수행하는 클래스인 동시에 스프링 시큐리티에 인가/인증 작업에 사용 가능
 */
@Slf4j
@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User {

    public ClubAuthMemberDTO(String username,
                             String password,
                             boolean fromSocial,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;
    }

    private String email;
    private String name;
    private boolean fromSocial;
}
