package com.github.white.at.framework.security.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.github.white.at.framework.web.ApiResult;

public class UserLogoutSuccessHandler implements LogoutSuccessHandler, RenderResponse {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) {
        render(httpServletResponse, ApiResult.success());
    }
}
