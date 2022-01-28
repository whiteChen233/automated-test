package com.github.white.at.framework.security.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.github.white.at.framework.web.ApiResult;
import com.github.white.at.framework.web.LoginUser;
import com.github.white.at.utils.RedisUtil;

import cn.hutool.extra.spring.SpringUtil;

public class UserLogoutHandler implements LogoutHandler, RenderResponse {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        SpringUtil.getBean(RedisUtil.class).delete(loginUser.getUuid());
        render(response, ApiResult.success());
    }
}
