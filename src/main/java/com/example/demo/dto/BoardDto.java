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
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String authorEmail;
    private Long views;
    private String category; // 공지사항, 일반, 질문, 자유게시판
    private String status; // 공개, 비공개, 삭제됨
    private String createdAt;
    private String updatedAt;
}

