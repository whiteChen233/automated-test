package com.github.white.at;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * AppServletInitializer: The type App servlet initializer.
 *
 * @author White
 * @version 1.0
 * @date 2021/11/23 13:56
 */
public class AppServletInitializer extends SpringBootServletInitializer {

    /**
     * Configure spring application builder.
     *
     * @param builder the builder
     * @return the spring application builder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AutomatedTestApplication.class);
    }
}
