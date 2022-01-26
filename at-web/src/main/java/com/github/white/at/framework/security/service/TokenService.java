package com.github.white.at.framework.security.service;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
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
import com.github.white.at.framework.response.ResponseCode;
import com.github.white.at.utils.RedisUtil;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
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
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenService implements InitializingBean {

    private static final String KEY_AUTHORITIES = "auths";

    @Getter
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

    public void createToken(LoginUser loginUser) {
        String uuid = IdUtil.randomUUID();
        loginUser.setUuid(uuid);
        Map<String, Object> claims = MapUtil.newHashMap();
        claims.put("id", uuid);
        claims.put("username", loginUser.getUsername());
        claims.put(KEY_AUTHORITIES, loginUser.getAuthorities());
        String token = jwtProperties.getTokenPrefix() + Jwts.builder()
            .addClaims(claims)
            .setId(uuid)
            .setIssuedAt(new Date())
            .compressWith(CompressionCodecs.DEFLATE)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
        loginUser.setToken(token);
        SpringUtil.getBean(RedisUtil.class).ops4Value().set(uuid, loginUser);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaimsFromToken(token).getBody();
        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(KEY_AUTHORITIES).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        LoginUser principal = LoginUser.builder().build();
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
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

    private Jws<Claims> getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        } catch (Exception e) {
            throw new BaseException(ResponseCode.FAIL.getCode(), "");
        }
    }
}
