package com.example.sakilademo.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig
{
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //System.out.println("Added cors mapping");
                registry.addMapping("/**").allowedOrigins("http://localhost:5173");
                registry.addMapping("/**").allowedOrigins("http://18.168.220.61");
                registry.addMapping("/**").allowedOrigins("http://90.243.145.51");
            }
        };
    }
}