package com.github.white.at.common.utils;

import java.time.format.DateTimeFormatter;

public enum DateFormatter {

    /** yyyy-MM-dd HH:mm:ss */
    Y4_M2_D2_H2_M2_S2("yyyy-MM-dd HH:mm:ss", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    ;

    private final String pattern;

    private final DateTimeFormatter formatter;

    DateFormatter(String pattern, DateTimeFormatter formatter) {
        this.pattern = pattern;
        this.formatter = formatter;
    }

    public DateTimeFormatter get() {
        return formatter;
    }

    public String getPattern() {
        return pattern;
    }
}
