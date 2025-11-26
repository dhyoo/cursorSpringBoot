package com.softone.prj.service;

import com.softone.prj.dto.ActivityDto;
import com.softone.prj.entity.Activity;
import com.softone.prj.mapper.EntityMapper;
import com.softone.prj.repository.ActivityRepository;
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
