package com.github.white.at.framework.security.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.github.white.at.framework.core.domain.ApiResult;

@Component
public class AccessDeniedHandlerimpl implements AccessDeniedHandler, ResponseData {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) {
        response(httpServletResponse, ApiResult.error(HttpStatus.FORBIDDEN.value(), "AccessDenied"));
    }
}
