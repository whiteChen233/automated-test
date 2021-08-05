package com.github.white.at.framework.security.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.github.white.at.framework.core.domain.ApiResult;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler, ResponseData {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) {
        response(httpServletResponse, ApiResult.ok());
    }
}
