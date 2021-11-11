package com.github.white.at.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.white.at.repository.entity.SysUserDO;

@RestController
public class BaseController {

    @GetMapping
    public String hello() {
        return "hello " + new SysUserDO();
    }
}
