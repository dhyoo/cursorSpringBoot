package com.softone.prj.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "페이징 요청")
public class PageRequest {
    
    @Builder.Default
    @Schema(description = "페이지 번호 (1부터 시작)", example = "1", defaultValue = "1")
    private int page = 1;
    
    @Builder.Default
    @Schema(description = "페이지당 항목 수", example = "10", defaultValue = "10")
    private int size = 10;
    
    @Schema(description = "정렬 기준 필드", example = "createdAt")
    private String sortBy;
    
    @Builder.Default
    @Schema(description = "정렬 방향 (ASC, DESC)", example = "DESC", defaultValue = "DESC")
    private String sortDirection = "DESC";
    
    /**
     * JPA Pageable로 변환
     */
    public org.springframework.data.domain.Pageable toPageable() {
        org.springframework.data.domain.Sort sort = org.springframework.data.domain.Sort.unsorted();
        
        if (sortBy != null && !sortBy.isEmpty()) {
            sort = "ASC".equalsIgnoreCase(sortDirection) 
                ? org.springframework.data.domain.Sort.by(sortBy).ascending()
                : org.springframework.data.domain.Sort.by(sortBy).descending();
        }
        
        return org.springframework.data.domain.PageRequest.of(page - 1, size, sort);
    }
    
    /**
     * MyBatis용 offset 계산
     */
    public int getOffset() {
        return (page - 1) * size;
    }
    
    /**
     * MyBatis용 limit
     */
    public int getLimit() {
        return size;
    }
    
    /**
     * 유효성 검증 및 기본값 설정
     */
    public void validate() {
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 10;
        }
        if (size > 100) {
            size = 100; // 최대 100개로 제한
        }
        if (sortDirection == null || (!sortDirection.equalsIgnoreCase("ASC") && !sortDirection.equalsIgnoreCase("DESC"))) {
            sortDirection = "DESC";
        }
    }
}


