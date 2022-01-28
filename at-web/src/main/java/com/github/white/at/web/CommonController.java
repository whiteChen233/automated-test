package com.github.white.at.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.github.white.at.framework.web.ApiResult;

@Controller
public class CommonController {

    @GetMapping("test")
    public ApiResult login() {
        return ApiResult.success("Hello world!");
    }
}
