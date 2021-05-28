package com.github.whitechen233.at;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AutomatedTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomatedTestApplication.class, args);
    }

}
