package com.github.whitechen233.at.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.whitechen233.at.api.model.UserModel;

@RestController
public class BaseController {

    @GetMapping
    public String hello() {
        return "hello " + new UserModel();
    }
}
