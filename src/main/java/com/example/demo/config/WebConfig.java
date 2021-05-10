package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    /**
     * To Add Cors Mappings.
     * @param registry registry
     */
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "OPTIONS", "HEAD",
                        "PUT", "PATCH", "DELETE", "TRACE")
                .allowedHeaders("*")
                .maxAge(86400)
                .exposedHeaders("Authorization");
    }
}
