package com.example.ex06.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * DTO 역할을 수행하는 클래스인 동시에 스프링 시큐리티에 인가/인증 작업에 사용 가능
 */
@Slf4j
@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User implements OAuth2User {


    public ClubAuthMemberDTO(String username,
                             String password,
                             boolean fromSocial,
                             Collection<? extends GrantedAuthority> authorities,
                             Map<String, Object> attr) {
        this(username, password, fromSocial, authorities);
        this.attr = attr;
    }

    public ClubAuthMemberDTO(String username,
                             String password,
                             boolean fromSocial,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;
    }

    private String email;
    private String password;
    private String name;
    private boolean fromSocial;
    private Map<String, Object> attr;

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
