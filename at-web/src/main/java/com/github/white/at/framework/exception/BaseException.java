package com.github.white.at.framework.exception;

import lombok.Getter;

@Getter
public class BaseException extends NestedException {

    private final int code;

    public BaseException(int code, String s) {
        super(s);
        this.code = code;
    }

    public BaseException(int code, String s, Throwable t) {
        super(s, t);
        this.code = code;
    }
}
