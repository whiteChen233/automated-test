package com.github.white.at.framework.security.handle;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.github.white.at.framework.response.ApiResult;

/**
 * ResponseData: The interface Response data.
 *
 * @author White
 * @version 1.0
 * @date 2021/07/13 23:53
 */
public interface ResponseData extends Serializable {

    /**
     * Response.
     *
     * @param response the response
     * @param result   the result
     */
    default void response(HttpServletResponse response, ApiResult result) {
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().print(result.toString());
        } catch (IOException e) {
            //
        }
    }
}
