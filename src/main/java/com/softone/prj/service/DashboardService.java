package com.softone.prj.service;

import com.softone.prj.dto.DashboardStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    public DashboardStatsDto getDashboardStats() {
        // 실제 구현에서는 Repository에서 조회
        return DashboardStatsDto.builder()
                .totalUsers(156)
                .activeUsers(89)
                .totalPosts(234)
                .todayVisitors(45)
                .build();
    }
}
