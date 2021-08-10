package com.github.white.at;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class AutomatedTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomatedTestApplication.class, args);
    }

}