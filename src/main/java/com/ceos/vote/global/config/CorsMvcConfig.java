package com.ceos.vote.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000", // 로컬 개발 환경
                        "http://localhost:8080",
                        "http://52.79.122.106", // EC2 서버
                        "http://52.79.122.106:8081",
                        "http://52.79.122.106:8080"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*");
    }
}
