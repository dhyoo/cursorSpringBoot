package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Swagger UI 및 OpenAPI 문서 허용
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/api-docs/**",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()
                // API 엔드포인트 허용
                .requestMatchers("/api/**").permitAll()
                // Hello World 엔드포인트 허용
                .requestMatchers("/", "/hello").permitAll()
                // H2 Console 허용 (개발 환경)
                .requestMatchers("/h2-console/**").permitAll()
                // 나머지 모든 요청은 인증 필요 (현재는 개발 단계이므로 permitAll)
                .anyRequest().permitAll()
            )
            // H2 Console을 위한 프레임 옵션 비활성화
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
            )
            // CSRF 비활성화 (개발 환경, API 사용 시)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**", "/api/**")
            )
            // OAuth2 로그인 비활성화 (현재는 사용하지 않음)
            .oauth2Login(oauth2 -> oauth2.disable());

        return http.build();
    }
}

