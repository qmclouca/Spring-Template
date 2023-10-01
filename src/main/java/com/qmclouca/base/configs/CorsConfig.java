package com.qmclouca.base.configs;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*") // Substitua com as origens permitidas (ou * para qualquer origem)
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos HTTP permitidos
//                .allowedHeaders("*"); // Headers permitidos
//    }
}
