package com.github.white.at.web;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    // private final TokenService tokenService;
    //
    // public CommonController(TokenService tokenService) {
    //     this.tokenService = tokenService;
    // }
    //
    // @PostMapping("login")
    // public ApiResult login(@RequestBody SysUserVO vo) {
    //     String token = tokenService.login(vo.getUsername(), vo.getPassword());
    //     return ApiResult.ok(Collections.singletonMap("token", token));
    // }
}
