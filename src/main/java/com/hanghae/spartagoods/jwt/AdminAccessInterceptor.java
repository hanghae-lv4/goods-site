package com.hanghae.spartagoods.jwt;

import com.hanghae.spartagoods.entity.MemberRoleEnum;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Component
@AllArgsConstructor
public class AdminAccessInterceptor implements HandlerInterceptor {
    private TokenValidator tokenValidator;
    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isPostRequestToProducts(request)) {
            String tokenValue = tokenManager.getTokenFromRequest(request);

            if (!checkAdmin(tokenValue)) {
                sendErrorMessage(request, response, "허가 되지 않은 사용자입니다.");
                return false; // 토큰 미검출, 유효성 검증 실패, 관리자 권한이 아닌 경우 요청 거부
            }
        }

        return true; // 관리자 권한 확인되면 요청 허용
    }

    private boolean isPostRequestToProducts(HttpServletRequest request) {
        return request.getMethod().equals("POST") && request.getRequestURI().equals("/products");
    }

    private boolean checkAdmin(String tokenValue) {
        if (tokenValue != null && tokenValue.startsWith(TokenValidator.BEARER_PREFIX)) {
            String token = tokenValidator.substringToken(tokenValue);

            if (tokenValidator.validateToken(token)) {
                Claims claims = tokenManager.getMemberInfoFromToken(token);

                if (claims.get(TokenManager.AUTHORIZATION_KEY).equals(MemberRoleEnum.ADMIN.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void sendErrorMessage(HttpServletRequest request, HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        ModelAndView modelAndView = new ModelAndView(jsonView);
        modelAndView.addObject("message", message);
        jsonView.render(modelAndView.getModel(), request, response);
    }
}