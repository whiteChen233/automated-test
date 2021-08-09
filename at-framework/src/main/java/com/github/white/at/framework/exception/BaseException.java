package com.github.white.at.framework.exception;

public class BaseException extends RuntimeException {

    public BaseException(Exception e) {
        super(e);
    }
}
