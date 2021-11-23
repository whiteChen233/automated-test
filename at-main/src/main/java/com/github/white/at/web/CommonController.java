package com.github.white.at.web;

import java.util.Collections;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.white.at.api.domain.SysUserVO;
import com.github.white.at.common.response.ApiResult;
import com.github.white.at.framework.security.service.TokenService;

@RestController
public class CommonController {

    private final TokenService tokenService;

    public CommonController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("login")
    public ApiResult login(@RequestBody SysUserVO vo) {
        String token = tokenService.login(vo.getUsername(), vo.getPassword());
        return ApiResult.ok(Collections.singletonMap("token", token));
    }
}
