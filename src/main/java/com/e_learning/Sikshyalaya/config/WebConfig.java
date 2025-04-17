package com.e_learning.Sikshyalaya.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Path to your folder on Desktop (adjust if necessary)
        Path uploadDir = Paths.get(System.getProperty("user.home"), "Desktop", "shikshyalaya");
        String uploadPath = uploadDir.toUri().toString();

        registry.addResourceHandler("/files/**")
                .addResourceLocations(uploadPath);
    }
}