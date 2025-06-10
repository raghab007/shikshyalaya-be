package com.e_learning.Sikshyalaya.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                                .allowedOrigins("http://localhost:5173")
                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                                .allowedHeaders("*")
                                .allowCredentials(true);
        }
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                String userHome = System.getProperty("user.home");

                // Course videos
                registry.addResourceHandler("/files/course/videos/**")
                                .addResourceLocations("file:" + userHome + "/Desktop/shikshyalaya/course/videos/")
                                .setCachePeriod(3600)
                                .resourceChain(true);

                // Course thumbnails
                registry.addResourceHandler("/files/course/thumbnails/**")
                                .addResourceLocations("file:" + userHome + "/Desktop/shikshyalaya/course/thumbnails/")
                                .setCachePeriod(3600)
                                .resourceChain(true);

                // Video feedback
                registry.addResourceHandler("/files/videofeedback/**")
                                .addResourceLocations("file:" + userHome + "/Desktop/shikshyalaya/videofeedback/")
                                .setCachePeriod(3600)
                                .resourceChain(true);

                // Course images
                registry.addResourceHandler("/files/course/images/**")
                                .addResourceLocations("file:" + userHome + "/Desktop/shikshyalaya/course/images/")
                                .setCachePeriod(3600)
                                .resourceChain(true);

                // Profile images
                registry.addResourceHandler("/files/profile/images/**")
                                .addResourceLocations("file:" + userHome + "/Desktop/shikshyalaya/profile/images/")
                                .setCachePeriod(3600)
                                .resourceChain(true);
        }
}