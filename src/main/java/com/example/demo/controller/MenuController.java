package com.example.demo.controller;

import com.example.demo.dto.MenuResponse;
import com.example.demo.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Menu API", description = "메뉴 조회 API")
public class MenuController {

    private final MenuService menuService;

    @Operation(
            summary = "메뉴 목록 조회",
            description = "사용자의 역할과 조직/팀 정보를 기반으로 접근 가능한 메뉴를 조회합니다."
    )
    @GetMapping("/menus")
    public ResponseEntity<MenuResponse> getMenus() {
        return ResponseEntity.ok(MenuResponse.success(menuService.getMenus()));
    }
}
