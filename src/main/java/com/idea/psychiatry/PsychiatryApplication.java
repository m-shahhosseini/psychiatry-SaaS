package com.idea.psychiatry;

import com.idea.psychiatry.modules.auth.config.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class PsychiatryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsychiatryApplication.class, args);
    }

}