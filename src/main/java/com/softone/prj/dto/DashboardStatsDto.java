package com.softone.prj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDto {
    private int totalUsers;
    private int activeUsers;
    private int totalPosts;
    private int todayVisitors;
}
