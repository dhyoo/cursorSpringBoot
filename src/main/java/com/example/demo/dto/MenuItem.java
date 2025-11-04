package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuItem {
    private String menuId;
    private String title;
    private String path;
    private String icon;
    private String permission; // READ, WRITE, DELETE, ADMIN
    private List<MenuItem> children;
}

