package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardStatsDto {
    private Long totalUsers;
    private String totalUsersChange; // 예: "+12% from last month"
    private Long activeSessions;
    private String activeSessionsChange;
    private Long todayVisits;
    private String todayVisitsChange;
}

