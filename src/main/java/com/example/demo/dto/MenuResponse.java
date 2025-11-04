package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    private boolean success;
    private List<MenuItem> data;
    private String timestamp;

    public static MenuResponse success(List<MenuItem> menus) {
        return MenuResponse.builder()
                .success(true)
                .data(menus)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}

