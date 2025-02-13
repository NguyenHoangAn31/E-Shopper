package com.example.security.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // String staticFolder = "file:///" + "D:/new_images/"; // Cập nhật đường dẫn
        // đến thư mục ngoài ổ D
        String staticFolder = "file:///" + System.getProperty("user.dir") + "/src/main/resources/static/";
        registry.addResourceHandler("/**").addResourceLocations(staticFolder);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}