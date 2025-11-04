package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot API Documentation")
                        .version("1.0.0")
                        .description("React 애플리케이션을 위한 Spring Boot API 문서")
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("로컬 개발 서버")));
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

            // 200 응답이 이미 있는 경우에만 공통 오류 응답 추가 (200 응답은 유지)
            operation.getResponses().putIfAbsent("400", badRequestResponse);
            operation.getResponses().putIfAbsent("404", notFoundResponse);
            operation.getResponses().putIfAbsent("500", internalServerErrorResponse);

            return operation;
        };
    }
}

