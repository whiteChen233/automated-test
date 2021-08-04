package com.github.white.at.framework.security.handle;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import com.github.white.at.common.core.domain.ApiResult;

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
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(result.toString());
        } catch (IOException e) {
            //
        }
    }
}
