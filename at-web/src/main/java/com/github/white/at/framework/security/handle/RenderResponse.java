package com.github.white.at.framework.security.handle;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.github.white.at.framework.response.ApiResult;
import com.github.white.at.utils.JSON;

/**
 * RenderResponse: The interface Render Response data.
 *
 * @author White
 * @version 1.0
 * @date 2021/07/13 23:53
 */
public interface RenderResponse extends Serializable {

    /**
     * render.
     *
     * @param response the response
     * @param result   the result
     */
    default void render(HttpServletResponse response, ApiResult result) {
        try {
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().print(JSON.INSTANCE.toString(result));
        } catch (IOException e) {
            //
        }
    }
}
