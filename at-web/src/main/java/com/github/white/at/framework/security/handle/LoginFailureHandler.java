package com.github.white.at.framework.security.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.github.white.at.framework.response.ApiResult;

public class LoginFailureHandler implements AuthenticationFailureHandler, RenderResponse {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) {
        render(response, ApiResult.error(-2, e.getMessage()));
    }
}
