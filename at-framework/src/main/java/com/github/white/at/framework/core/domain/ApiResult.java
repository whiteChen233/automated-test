package com.github.white.at.framework.core.domain;

import java.util.HashMap;

import org.springframework.http.HttpStatus;

/**
 * The type Api result.
 */
public class ApiResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    public static final String KEY_CODE = "code";
    /**
     * 返回内容
     */
    public static final String KEY_MSG = "msg";
    /**
     * 数据对象
     */
    public static final String KEY_DATA = "data";

    /**
     * Instantiates a new Api result.
     */
    public ApiResult() {
    }

    /**
     * Instantiates a new Api result.
     *
     * @param code the code
     * @param msg  the msg
     * @param data the data
     */
    public ApiResult(int code, String msg, Object data) {
        super.put(KEY_CODE, code);
        super.put(KEY_MSG, msg);
        super.put(KEY_DATA, data);
    }

    /**
     * Instantiates a new Api result.
     *
     * @param status the status
     * @param data   the data
     */
    public ApiResult(HttpStatus status, Object data) {
        this(status.value(), status.getReasonPhrase(), data);
    }

    /**
     * Ok api result.
     *
     * @param data the data
     * @return the api result
     */
    public static ApiResult ok(Object data) {
        return new ApiResult(HttpStatus.OK, data);
    }

    /**
     * Ok api result.
     *
     * @return the api result
     */
    public static ApiResult ok() {
        return ok(null);
    }

    /**
     * Error api result.
     *
     * @param code the code
     * @param msg  the msg
     * @return the api result
     */
    public static ApiResult error(int code, String msg) {
        return new ApiResult(code, msg, null);
    }

    /**
     * Error api result.
     *
     * @return the api result
     */
    public static ApiResult error() {
        return new ApiResult(HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}
