package com.example.ex06.security.filter;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.catalina.filters.CorsFilter.*;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // 모든 필터에서 가장 먼저 동작
public class CORSFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS, "*");
        response.setHeader(RESPONSE_HEADER_ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept, Key, Authorization");

        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
