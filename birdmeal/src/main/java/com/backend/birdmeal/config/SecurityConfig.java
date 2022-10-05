package com.backend.birdmeal.config;

import com.backend.birdmeal.jwt.JwtAccessDeniedHandler;
import com.backend.birdmeal.jwt.JwtAuthenticationEntryPoint;
import com.backend.birdmeal.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // @preAuthorize 어노테이션을 메소드 단위로 추가하기 위해
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CorsFilter corsFilter;


    public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler, CorsFilter corsFilter) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.corsFilter = corsFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //보안 예외처리를 위한 메서드
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/favicon.ico",
                        "/error",
                        "/configuration/**"
                );
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.addAllowedOrigin("*");
                    cors.addAllowedHeader("*");
                    cors.addAllowedMethod("*");
                    return cors;
                })
                .and()
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf().disable()

                // Custom Jwt 토큰 필터를 filter chain의 UsernamePasswordAuthenticationFilter 앞에 세팅
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()//예외처리 기능 수행
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)//인증 실패 진입점
                .accessDeniedHandler(jwtAccessDeniedHandler)//인가 실패 진입점

                //세션 사용하지 않기때문에 STATELESS사용
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/api/user/**").permitAll() // 로그인
                .antMatchers("/api/user/register").permitAll() // 관리자 권한 허용
                .antMatchers("/api/seller/**").permitAll() // 관리자 권한
                .antMatchers("/api/file/**").permitAll() // 파일 업로드
                .antMatchers("/api/donation/**").hasRole("ADMIN")
                .antMatchers("/api/product/**").hasRole("ADMIN")
                .antMatchers("/api/order/**").hasRole("ADMIN")
                .antMatchers("/api/nft/**").hasRole("ADMIN")
                .antMatchers("/api/donation/**").hasRole("CHILD")
                .antMatchers("/api/product/**").hasRole("CHILD")
                .antMatchers("/api/order/**").hasRole("CHILD")
                .antMatchers("/api/nft/**").hasRole("CHILD")

                .anyRequest().hasRole("ADMIN")

                .and()
                .apply(new JwtSecurityConfig(tokenProvider)); // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig 적용

    }

}