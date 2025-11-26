package com.softone.prj.controller;

import com.softone.prj.dto.ApiResponse;
import com.softone.prj.dto.DashboardStatsDto;
import com.softone.prj.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard API", description = "대시보드 통계 API")
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "대시보드 통계 조회", description = "대시보드에 표시할 통계 정보를 조회합니다")
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<DashboardStatsDto>> getDashboardStats() {
        return ResponseEntity.ok(ApiResponse.success(dashboardService.getDashboardStats()));
    }
}
