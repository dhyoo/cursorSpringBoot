package com.example.demo.service;

import com.example.demo.dto.DashboardStatsDto;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;

    public DashboardStatsDto getDashboardStats() {
        long totalUsers = userRepository.count();
        
        return DashboardStatsDto.builder()
                .totalUsers(totalUsers)
                .totalUsersChange("+12% from last month")
                .activeSessions(567L)
                .activeSessionsChange("+8% from last month")
                .todayVisits(89L)
                .todayVisitsChange("-3% from yesterday")
                .build();
    }
}
