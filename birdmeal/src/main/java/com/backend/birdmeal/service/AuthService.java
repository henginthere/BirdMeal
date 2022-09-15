package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.ResponseLoginDto;
import com.backend.birdmeal.dto.TokenDto;
import com.backend.birdmeal.entity.UserEntity;
import com.backend.birdmeal.jwt.TokenProvider;
import com.backend.birdmeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    UserRepository userRepository;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    // 로그인 관련 메서드
    public ResponseLoginDto authorize(String email) {


        String pass = email.split("@")[0];

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email,pass);
        //실제 검증이 일어나는 부분
        //authenticate 메서드가 실행될 때

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authorities = getAuthorities(authentication);

        logger.info("권한 : "+authorities);
        System.out.println("권한 : "+authorities);
        TokenDto tokenDto;
        Long seq;

        UserEntity user = userRepository.findByUserEmail(email).get();

        if(user==null){
            return null;
        }

        else{
            seq = user.getUserSeq();
            tokenDto = tokenProvider.createUserToken(authentication.getName(), authorities);
        }

        return new ResponseLoginDto(seq, tokenDto);

    }

    // 토큰 재발급 관련 메서드
    public TokenDto reissue(String requestAccessToken, String requestRefreshToken) {
        if (!tokenProvider.validateToken(requestRefreshToken)) {
//            throw new UnauthorizedException("유효하지 않은 RefreshToken 입니다");
            //재로그인 요청반환

        }

        Authentication authentication = tokenProvider.getAuthentication(requestRefreshToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authorities = getAuthorities(authentication);
        String userEmail = userRepository.findByUserSeq(Long.parseLong(authentication.getName())).get().getUserEmail();
        return tokenProvider.createUserToken(userEmail,authorities);

    }

    // 권한 가져오기
    public String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
//
//    public void logout(String accessToken, String refreshToken) {
//        redisUtil.setBlackList(accessToken, "accessToken", 1800);
//        redisUtil.setBlackList(refreshToken, "refreshToken", 60400);
//    }
}
