package study.market.etc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private final UserDetailsService userService;
    private final CustomAuthFailureHandler failureHandler;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 스프링 시큐리티 적용X
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/static/**", "/h2-console/**");
    }

    // 스프링 시큐리티 적용
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/member/login", "/", "/member/signup", "/member/checkEmail", "/mail/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/member/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/")
                        .failureHandler(failureHandler))
                .logout(logout -> logout
                        .logoutSuccessUrl("/member/login")
                        .invalidateHttpSession(true))
                .csrf(csrf -> csrf
                        .disable())
                .build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return daoAuthenticationProvider;
    }

}
