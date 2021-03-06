package com.github.white.at.framework.web;

import java.util.HashMap;

import org.springframework.http.HttpStatus;

/**
 * ApiResult: The type Api result.
 *
 * @author White
 * @version 1.0
 * @date 2021/11/22 13:56
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
     * @param responseCode the response code
     * @param data         the data
     */
    public ApiResult(ResponseCode responseCode, Object data) {
        this(responseCode.getCode(), responseCode.getKey(), data);
    }

    public ApiResult(HttpStatus status, Object data) {
        this(status.value(), status.getReasonPhrase(), data);
    }

    /**
     * Success api result.
     *
     * @param data the data
     * @return the api result
     */
    public static ApiResult success(Object data) {
        return new ApiResult(ResponseCode.SUCCESS, data);
    }

    /**
     * Success api result.
     *
     * @return the api result
     */
    public static ApiResult success() {
        return success(null);
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
        return new ApiResult(ResponseCode.FAIL, null);
    }
}
