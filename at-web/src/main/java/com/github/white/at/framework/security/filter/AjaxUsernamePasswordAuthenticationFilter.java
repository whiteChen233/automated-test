package com.github.white.at.framework.security.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.extra.spring.SpringUtil;

public class AjaxUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Map<String, String> param;

    public AjaxUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        process(request);
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return param.get(this.getUsernameParameter());
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return param.get(this.getPasswordParameter());
    }

    private void process(HttpServletRequest request) {
        try (InputStream is = request.getInputStream()) {
            param = SpringUtil.getBean(ObjectMapper.class)
                .readValue(is, new TypeReference<HashMap<String, String>>() {
                });
        } catch (IOException e) {
            // log
        }
    }
}
