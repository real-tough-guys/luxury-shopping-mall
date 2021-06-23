package com.skhu.luxuryshop.user.config;

import com.skhu.luxuryshop.user.encoder.BCryptPasswordEncoder;
import com.skhu.luxuryshop.user.jwt.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .excludePathPatterns("api/users/**")
                .excludePathPatterns("api/products/**")
                .excludePathPatterns("api/carts/**")
                .excludePathPatterns("api/orders/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}