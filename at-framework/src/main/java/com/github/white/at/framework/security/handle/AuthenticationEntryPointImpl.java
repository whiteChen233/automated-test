package com.github.white.at.framework.security.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.github.white.at.common.response.ApiResult;

/**
 * AuthenticationEntryPointImpl: The type Authentication entry point.
 *
 * @author White
 * @version 1.0
 * @date 2021/07/13 23:56
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, ResponseData {

    private static final long serialVersionUID = -1L;

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) {
        int code = HttpStatus.UNAUTHORIZED.value();
        String msg = String.format("请求访问：%s，认证失败，无法访问系统资源", httpServletRequest.getRequestURI());
        response(httpServletResponse, ApiResult.error(code, msg));
    }
}
