package com.github.whitechen233.at.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("security")
public class TestSecurityController {

    @GetMapping("echo")
    public String demo() {
        return "公共";
    }

    @GetMapping("home")
    public String home() {
        return "主页";
    }

    @GetMapping("admin")
    public String admin() {
        return "Admin";
    }

    @GetMapping("normal")
    public String normal() {
        return "Normal";
    }
}
