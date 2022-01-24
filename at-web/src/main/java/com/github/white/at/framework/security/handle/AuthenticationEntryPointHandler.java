package com.github.white.at.framework.security.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.github.white.at.framework.response.ApiResult;

public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint, RenderResponse {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) {
        String msg = String.format("请求访问：%s，认证失败，无法访问系统资源", httpServletRequest.getRequestURI());
        render(httpServletResponse, ApiResult.error(-1, msg));
    }
}
