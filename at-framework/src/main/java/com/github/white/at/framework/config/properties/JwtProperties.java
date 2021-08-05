package com.github.white.at.framework.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String header;

    private String tokenPrefix;

    private String base64Secret;

    public String getTokenPrefix() {
        return tokenPrefix + " ";
    }
}
