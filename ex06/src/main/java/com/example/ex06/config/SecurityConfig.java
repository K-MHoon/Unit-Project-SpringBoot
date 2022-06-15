package com.example.ex06.config;

import com.example.ex06.security.filter.ApiCheckFilter;
import com.example.ex06.security.filter.ApiLoginFilter;
import com.example.ex06.security.handler.ApiLoginFailHandler;
import com.example.ex06.security.handler.ClubLoginSuccessHandler;
import com.example.ex06.security.service.ClubUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Slf4j
@RequiredArgsConstructor
// securedEnabled = 예전 버전의 @Secure 어노테이션 사용 가능 여부
// prePostEnable = @PreAuthorize 사용 가능 여부
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ClubUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.inMemoryAuthentication().withUser("user1")
//                .password("$2a$10$oOOjRESwzOVIcEdCAIV5L.39q3FPGyCcxZF0DKEHumqGtXP3Q3y4.")
//                .roles("USER");
//    }


    // 접근제한 처리
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/sample/all").permitAll()
//                .antMatchers("/sample/member").hasRole("USER");
        http.formLogin(); // 인증/인가 절차에서 문제가 발생했을 때 로그인 페이지를 보여주도록 지정할 수 있고, 화면으로 로그인 방식을 지원한다는 의미로 사용된다.
        http.csrf().disable();
        http.logout();
        http.oauth2Login()
                .successHandler(successHandler());
        http.rememberMe().tokenValiditySeconds(60*60*24*7) // 7days
                .userDetailsService(userDetailsService);
        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(apiLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter("/notes/**/*");
    }

    @Bean
    public ApiLoginFilter apiLoginFilter() throws Exception {

        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login");
        apiLoginFilter.setAuthenticationManager(authenticationManager());
        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());

        return apiLoginFilter;
    }

    @Bean
    public ClubLoginSuccessHandler successHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }
}
