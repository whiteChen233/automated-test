package com.github.white.at.framework.security.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.github.white.at.framework.response.ApiResult;

public class LoginSuccessHandler implements AuthenticationSuccessHandler, RenderResponse {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        render(response, ApiResult.success(authentication));
    }
}
