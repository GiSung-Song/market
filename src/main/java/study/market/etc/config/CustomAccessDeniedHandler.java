package study.market.etc.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        String ajaxHeader = request.getHeader("X-Requested-With");

        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);

        //ajax 호출인 경우 view 에서 처리
        if (isAjax) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "허용되지 않은 사용자입니다.");
        }

        response.sendRedirect("/error/403");
    }
}
