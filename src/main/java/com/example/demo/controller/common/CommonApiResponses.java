package com.example.demo.controller.common;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "성공"),
    @ApiResponse(responseCode = "400", description = "잘못된 요청", 
            content = @Content(schema = @Schema(implementation = com.example.demo.dto.ApiResponse.class))),
    @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
            content = @Content(schema = @Schema(implementation = com.example.demo.dto.ApiResponse.class))),
    @ApiResponse(responseCode = "500", description = "서버 내부 오류",
            content = @Content(schema = @Schema(implementation = com.example.demo.dto.ApiResponse.class)))
})
public @interface CommonApiResponses {
}

