package com.backend.birdmeal.jwt;

import com.backend.birdmeal.dto.TokenDto;
import com.backend.birdmeal.entity.UserEntity;
import com.backend.birdmeal.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private final UserRepository userRepository;
    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long accessTokenValidityInMilliseconds ;
    private final long refreshTokenValidityInMilliseconds;

    private Key key;


    public TokenProvider(UserRepository userRepository, @Value("${jwt.secret}") String secret,
                         @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
                         @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds) {
        this.userRepository = userRepository;
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto createUserDto(String email,
                                       String authorities) {
        long now = (new Date()).getTime();

//        Manager manager = managerRepository.findBymanagerId(id).orElseThrow(()->new ManagerNotFoundException("가입되지 않은 정보입니다."));
        UserEntity user = userRepository.findByUserEmail(email);

        //claim에 managerSeq정보 추가
        String accessToken = Jwts.builder()
                .claim("userSeq", user.getUserSeq())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(now + accessTokenValidityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        HttpHeaders httpHeaders = new HttpHeaders();
        // jwt를 response header에 넣어줌
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        String refreshToken = Jwts.builder()
                .claim(AUTHORITIES_KEY, authorities)
                .claim("userSeq", user.getUserEmail())
                .setExpiration(new Date(now + refreshTokenValidityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return new TokenDto(accessToken, refreshToken);
    }

    /*
     * 권한 가져오는 메서드
     */
    public Authentication getAuthentication(String token) {

        //Token으로 claim 생성
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 빼낸 권한정보로 User객체를 만듦
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(claims.get("userSeq"), token, authorities);
//
//        if(claims.get(AUTHORITIES_KEY).toString().equals("ROLE_ADMIN")) {
//            return new UsernamePasswordAuthenticationToken(claims.get("managerSeq"), token, authorities);
//        }
//        else{
//            return new UsernamePasswordAuthenticationToken(claims.get("consultantSeq"), token, authorities);
//        }

    }
    /*
     * 토큰 유효성 검사하는 메서드
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            logger.info("validate 들어옴");
//            if (redisUtil.hasKeyBlackList(token)) {
//                throw new UnauthorizedException("이미 탈퇴한 회원입니다");
//            }
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
//        catch (UnauthorizedException e) {
//            logger.info("이미 탈퇴한 회원입니다.");
//        }
        return false;
    }

    public Claims getClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
