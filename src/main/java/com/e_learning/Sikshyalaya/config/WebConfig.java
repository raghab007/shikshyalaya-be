package com.e_learning.Sikshyalaya.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Add your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure course images
        String courseImagesPath = System.getProperty("user.home") + "/Desktop/shikshyalaya/course/images";
        registry.addResourceHandler("/files/course/images/**")
                .addResourceLocations("file:" + courseImagesPath + "/");

        // Configure profile images
        String profileImagesPath = System.getProperty("user.home") + "/Desktop/shikshyalaya/profile/images";
        File profileDir = new File(profileImagesPath);
        if (!profileDir.exists()) {
            profileDir.mkdirs();
        }
        registry.addResourceHandler("/files/profile/images/**")
                .addResourceLocations("file:" + profileImagesPath + "/");
    }
}