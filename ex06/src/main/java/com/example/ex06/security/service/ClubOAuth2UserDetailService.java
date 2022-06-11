package com.example.ex06.security.service;

import com.example.ex06.entity.club.ClubMember;
import com.example.ex06.enums.club.ClubMemberRole;
import com.example.ex06.repository.club.ClubMemberRepository;
import com.example.ex06.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubOAuth2UserDetailService extends DefaultOAuth2UserService {

    private final ClubMemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("----------------------");
        log.info("userRequest : {}", userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName : {}", clientName);
        log.info("userRequest.getAdditionalParameters() : {}", userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        oAuth2User.getAttributes().forEach((k,v) -> {
            log.info("{} : {}", k , v);
        });

        String email = null;

        if(clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }

        log.info("EMAIL : {}", email);

        ClubMember member = saveSocialMember(email);

        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                true,
                member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        clubAuthMember.setName(member.getName());

        return clubAuthMember;
    }

    @Transactional
    public ClubMember saveSocialMember(String email) {

        // 기존에 동일한 이메일로 가입한 회원이 있는 경우 조회만
        Optional<ClubMember> result = repository.findByEmail(email, true);

        if(result.isPresent()) {
            return result.get();
        }

        ClubMember clubMember = ClubMember.builder()
                .name(email)
                .email(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);
        repository.save(clubMember);

        return clubMember;
    }
}
