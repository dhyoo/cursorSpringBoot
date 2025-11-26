package com.softone.prj.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot API Documentation")
                        .version("1.0.0")
                        .description("React 애플리케이션을 위한 Spring Boot API 문서 (JWT 인증 포함)")
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("로컬 개발 서버")))
                // JWT 인증 스키마 추가
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT 토큰을 입력하세요 (Bearer 접두사 제외)")))
                // 전역 보안 요구사항 추가
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            // 공통 응답 스키마 정의
            Schema<?> apiResponseSchema = new Schema<>()
                    .type("object")
                    .addProperty("success", new Schema<>().type("boolean"))
                    .addProperty("data", new Schema<>().type("object"))
                    .addProperty("message", new Schema<>().type("string"))
                    .addProperty("timestamp", new Schema<>().type("string"));

            // 공통 응답 정의
            ApiResponse badRequestResponse = new ApiResponse()
                    .description("잘못된 요청")
                    .content(new Content()
                            .addMediaType("application/json",
                                    new MediaType().schema(apiResponseSchema)));

            ApiResponse notFoundResponse = new ApiResponse()
                    .description("리소스를 찾을 수 없음")
                    .content(new Content()
                            .addMediaType("application/json",
                                    new MediaType().schema(apiResponseSchema)));

            ApiResponse internalServerErrorResponse = new ApiResponse()
                    .description("서버 내부 오류")
                    .content(new Content()
                            .addMediaType("application/json",
                                    new MediaType().schema(apiResponseSchema)));

            // 인증이 필요한 엔드포인트에 401 응답 추가
            ApiResponse unauthorizedResponse = new ApiResponse()
                    .description("인증되지 않음 (JWT 토큰 필요)")
                    .content(new Content()
                            .addMediaType("application/json",
                                    new MediaType().schema(apiResponseSchema)));

            // 공통 오류 응답 추가
            operation.getResponses().putIfAbsent("400", badRequestResponse);
            operation.getResponses().putIfAbsent("401", unauthorizedResponse);
            operation.getResponses().putIfAbsent("404", notFoundResponse);
            operation.getResponses().putIfAbsent("500", internalServerErrorResponse);

            return operation;
        };
    }
}
