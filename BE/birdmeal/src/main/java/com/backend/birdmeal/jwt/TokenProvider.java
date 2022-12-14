package com.backend.birdmeal.jwt;

import com.backend.birdmeal.dto.TokenDto;
import com.backend.birdmeal.entity.SellerEntity;
import com.backend.birdmeal.entity.UserEntity;
import com.backend.birdmeal.repository.SellerInfoRepository;
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
    private final SellerInfoRepository sellerInfoRepository;
    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    private Key key;


    public TokenProvider(UserRepository userRepository, SellerInfoRepository sellerInfoRepository, @Value("${jwt.secret}") String secret,
                         @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
                         @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds) {
        this.userRepository = userRepository;
        this.sellerInfoRepository = sellerInfoRepository;
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto createUserToken(String email,
                                    String authorities) {
        long now = (new Date()).getTime();

        String accessToken;
        String refreshToken;

        if (authorities.equals("ROLE_SELLER")) {
            SellerEntity seller = sellerInfoRepository.findBySellerEmail(email);

//claim??? managerSeq?????? ??????
            accessToken = Jwts.builder()
                    .claim("userSeq", seller.getSellerSeq())
                    .claim(AUTHORITIES_KEY, authorities)
                    .setExpiration(new Date(now + accessTokenValidityInMilliseconds))
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();

            HttpHeaders httpHeaders = new HttpHeaders();
            // jwt??? response header??? ?????????
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

            refreshToken = Jwts.builder()
                    .claim(AUTHORITIES_KEY, authorities)
                    .claim("userSeq", seller.getSellerEmail())
                    .setExpiration(new Date(now + refreshTokenValidityInMilliseconds))
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();
        } else {
//        Manager manager = managerRepository.findBymanagerId(id).orElseThrow(()->new ManagerNotFoundException("???????????? ?????? ???????????????."));
            UserEntity user = userRepository.findByUserEmail(email).get();

            //claim??? managerSeq?????? ??????
            accessToken = Jwts.builder()
                    .claim("userSeq", user.getUserSeq())
                    .claim(AUTHORITIES_KEY, authorities)
                    .setExpiration(new Date(now + accessTokenValidityInMilliseconds))
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();

            HttpHeaders httpHeaders = new HttpHeaders();
            // jwt??? response header??? ?????????
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

            refreshToken = Jwts.builder()
                    .claim(AUTHORITIES_KEY, authorities)
                    .claim("userSeq", user.getUserEmail())
                    .setExpiration(new Date(now + refreshTokenValidityInMilliseconds))
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();
        }
        return new TokenDto(accessToken, refreshToken);
    }

    /*
     * ?????? ???????????? ?????????
     */
    public Authentication getAuthentication(String token) {

        //Token?????? claim ??????
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // ?????? ??????????????? User????????? ??????
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
     * ?????? ????????? ???????????? ?????????
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            logger.info("validate ?????????");
//            if (redisUtil.hasKeyBlackList(token)) {
//                throw new UnauthorizedException("?????? ????????? ???????????????");
//            }
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("????????? JWT ???????????????.");
        } catch (ExpiredJwtException e) {
            logger.info("????????? JWT ???????????????.");
        } catch (UnsupportedJwtException e) {
            logger.info("???????????? ?????? JWT ???????????????.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT ????????? ?????????????????????.");
        }
//        catch (UnauthorizedException e) {
//            logger.info("?????? ????????? ???????????????.");
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
