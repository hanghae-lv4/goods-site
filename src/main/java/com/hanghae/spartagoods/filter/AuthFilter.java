package com.hanghae.spartagoods.filter;

import com.hanghae.spartagoods.entity.Member;
import com.hanghae.spartagoods.entity.MemberRoleEnum;
import com.hanghae.spartagoods.jwt.TokenManager;
import com.hanghae.spartagoods.jwt.TokenValidator;
import com.hanghae.spartagoods.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "AuthFilter")
@Component
@Order(1)
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final MemberRepository memberRepository;
    private final TokenManager tokenManager;
    private final TokenValidator tokenValidator;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) &&
            (url.startsWith("/signup") || url.startsWith("/signin") || url.startsWith("/products"))
        ) {
            // 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행
            chain.doFilter(request, response); // 다음 Filter 로 이동
        } else {

            String tokenValue = tokenManager.getTokenFromRequest(httpServletRequest);

            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                // JWT 토큰 substring
                String token = tokenValidator.substringToken(tokenValue);

                // 토큰 검증
                if (!tokenValidator.validateToken(token)) {
                    throw new IllegalArgumentException("Token Error");
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = tokenManager.getMemberInfoFromToken(token);

                Member member = memberRepository.findByEmail(info.getSubject()).orElseThrow(() ->
                    new NullPointerException("Not Found User")
                );

                request.setAttribute("member", member);
                chain.doFilter(request, response); // 다음 Filter 로 이동
            }
            throw new IllegalArgumentException("Not Found Token");
        }
    }
}
