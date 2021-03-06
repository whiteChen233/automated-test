package com.github.white.at.framework.web;

import lombok.Getter;

@Getter
public enum ResponseCode {

    /** 失败 */
    FAIL(-1, "fail"),
    /** 成功 */
    SUCCESS(0, "success"),

    /** 参数格式错误 */
    PARAMETER_ERROR(10001, "param-error"),


    ;

    private final int code;

    private final String key;

    ResponseCode(int code, String key) {
        this.code = code;
        this.key = key;
    }
}
