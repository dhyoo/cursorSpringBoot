package com.example.demo.service;

import com.example.demo.dto.ActivityDto;
import com.example.demo.entity.Activity;
import com.example.demo.mapper.EntityMapper;
import com.example.demo.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final EntityMapper mapper;

    public List<ActivityDto> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(mapper::toActivityDto)
                .collect(Collectors.toList());
    }
}
