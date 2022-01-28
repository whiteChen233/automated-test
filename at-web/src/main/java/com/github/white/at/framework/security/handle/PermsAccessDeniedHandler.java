package com.github.white.at.framework.security.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.github.white.at.framework.web.ApiResult;

public class PermsAccessDeniedHandler implements AccessDeniedHandler, RenderResponse {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) {
        render(httpServletResponse, ApiResult.error(HttpStatus.FORBIDDEN.value(), e.getMessage()));
    }
}
