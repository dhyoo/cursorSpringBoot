package com.example.demo.controller;

import com.example.demo.dto.ActivityDto;
import com.example.demo.dto.ApiResponse;
import com.example.demo.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
@Tag(name = "Activity API", description = "활동 로그 API")
public class ActivityController {

    private final ActivityService activityService;

    @Operation(summary = "활동 로그 조회", description = "최근 활동 로그를 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ActivityDto>>> getAllActivities() {
        return ResponseEntity.ok(ApiResponse.success(activityService.getAllActivities()));
    }
}
