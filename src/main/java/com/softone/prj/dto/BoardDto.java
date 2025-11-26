package com.softone.prj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String authorEmail;
    private Long views;
    private String category;
    private String status;
    private String createdAt;
    private String updatedAt;
}
