package com.example.adminservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();

                // Пробрасываем X-User-Role и X-User-Email
                String role = request.getHeader("X-User-Role");
                if (role != null) {
                    requestTemplate.header("X-User-Role", role);
                }
                String email = request.getHeader("X-User-Email");
                if (email != null) {
                    requestTemplate.header("X-User-Email", email);
                }

                // И Authorization (если auth-service его парсит)
                String auth = request.getHeader("Authorization");
                if (auth != null) {
                    requestTemplate.header("Authorization", auth);
                }
            }
        };
    }
}