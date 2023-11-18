package com.hanghae.spartagoods.config;

import com.hanghae.spartagoods.jwt.AdminAccessInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AdminAccessInterceptor adminAccessInterCeptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAccessInterCeptor)
            .addPathPatterns("/products");
    }
}