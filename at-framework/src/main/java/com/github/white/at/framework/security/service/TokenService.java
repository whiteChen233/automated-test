package com.github.white.at.framework.security.service;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.github.white.at.framework.config.properties.JwtProperties;
import com.github.white.at.framework.core.domain.LoginUser;
import com.github.white.at.framework.exception.BaseException;
import com.google.common.collect.Maps;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenService implements InitializingBean {

    private static final String KEY_AUTHORITIES = "auths";

    private final JwtProperties jwtProperties;

    private Key key;

    public TokenService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getBase64Secret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(LoginUser loginUser) {
        Map<String, Object> claims = Maps.newHashMap();
        claims.put("id", loginUser.getId());
        claims.put("username", loginUser.getUsername());
        claims.put(KEY_AUTHORITIES, loginUser.getAuthorities());
        return Jwts.builder()
            .addClaims(claims)
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(new Date())
            .compressWith(CompressionCodecs.DEFLATE)
            .signWith(key, SignatureAlgorithm.ES512)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaimsFromToken(token).getBody();

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(KEY_AUTHORITIES).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        LoginUser principal = new LoginUser();
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            getClaimsFromToken(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return false;
    }

    private Jws<Claims> getClaimsFromToken(String token) throws BaseException {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }
}
