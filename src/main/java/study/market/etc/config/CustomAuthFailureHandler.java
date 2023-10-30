package study.market.etc.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;

public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage;

        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디가 존재하지 않거나 비밀번호가 틀립니다.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "시스템 문제입니다. 잠시 후 이용해주세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "해당 이메일로 가입된 정보가 없습니다. 회원가입을 진행해주세요.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "인증 요청이 거부되었습니다.";
        } else {
            errorMessage = "예상치 못한 오류가 발생하였습니다. 잠시 후 이용해주세요.";
        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/member/login?error=true&exception=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
