package com.wisedevlife.whytalkauth.service.implement;

import com.wisedevlife.whytalkauth.common.enums.ErrorCodeEnum;
import com.wisedevlife.whytalkauth.common.exception.AuthException;
import com.wisedevlife.whytalkauth.common.utils.JsonUtil;
import com.wisedevlife.whytalkauth.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${why-talk.app.jwt-secret}")
    private String jwtSecret;

    @Value("${why-talk.app.jwt-issuer}")
    private String issuer;

    @Override
    public String generateToken(String username, Duration duration) {
        return generateToken(new HashMap<>(), username, duration);
    }

    @Override
    public String generateToken(Map<String, Object> claims, String username, Duration duration) {
        Instant now = Instant.now();
        return Jwts.builder()
                .signWith(key(), SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(duration.getSeconds())))
                .compact();
    }

    @Override
    public <T> T getClaimBody(String token, Class<T> t) {
        Claims body =
                Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        return JsonUtil.convertFromMap(body, t);
    }

    @Override
    public String getClaimSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token. ", e);
            throw new AuthException(ErrorCodeEnum.AUTH_JWT_INVALID, HttpStatus.UNAUTHORIZED);
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired. ", e);
            throw new AuthException(ErrorCodeEnum.AUTH_JWT_EXPIRED, HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported. ", e);
            throw new AuthException(ErrorCodeEnum.AUTH_JWT_UNSUPPORTED, HttpStatus.UNAUTHORIZED);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty. ", e);
            throw new AuthException(ErrorCodeEnum.AUTH_JWT_EMPTY_CLAIMS, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("Unknown exception. ", e);
            throw new AuthException(ErrorCodeEnum.SYSTEM_ERROR, HttpStatus.UNAUTHORIZED);
        }
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
