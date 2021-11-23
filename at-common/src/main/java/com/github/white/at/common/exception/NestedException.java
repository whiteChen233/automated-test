package com.github.white.at.common.exception;

import java.util.Optional;

public class NestedException extends RuntimeException {

    public NestedException(String s) {
        super(s);
    }

    public NestedException(String s, Throwable t) {
        super(s, t);
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder(64);
        Throwable t = getCause();
        String msg = t.getMessage();
        Optional.ofNullable(msg).ifPresent(s -> sb.append(s).append("; "));
        sb.append("application nested exception is ").append(t);
        return sb.toString();
    }

    @Override
    public String toString() {
        String name = getClass().getName();
        return Optional.ofNullable(getLocalizedMessage()).map(i -> name + ": " + i).orElse(name);
    }
}
