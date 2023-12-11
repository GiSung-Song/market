package study.market.etc.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String ajaxHeader = request.getHeader("X-Requested-With");

        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);

        //ajax 호출인 경우 view 에서 처리
        if (isAjax) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증되지 않은 회원입니다.");
        }

        //controller 에서 예외 처리
        response.sendRedirect("/error/401");
    }
}
