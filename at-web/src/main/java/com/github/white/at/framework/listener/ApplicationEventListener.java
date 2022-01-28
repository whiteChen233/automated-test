package com.github.white.at.framework.listener;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.github.white.at.framework.web.LoginUser;
import com.github.white.at.utils.RedisUtil;

import cn.hutool.extra.spring.SpringUtil;

@Component
public class ApplicationEventListener {

    @EventListener(LogoutSuccessEvent.class)
    public void logoutSuccessEvent(LogoutSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        SpringUtil.getBean(RedisUtil.class).delete(loginUser.getUuid());
    }
}
