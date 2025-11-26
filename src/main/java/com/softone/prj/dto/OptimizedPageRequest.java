package com.softone.prj.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 최적화된 페이징 요청
 * COUNT 쿼리를 선택적으로 실행
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "최적화된 페이징 요청")
public class OptimizedPageRequest {
    
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
    
    @Builder.Default
    @Schema(description = "전체 개수 조회 여부 (false면 COUNT 쿼리 생략)", example = "true", defaultValue = "true")
    private boolean needTotalCount = true;
    
    /**
     * 유효성 검증
     */
    public void validate() {
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 10;
        }
        if (size > 100) {
            size = 100;
        }
        if (sortDirection == null || (!sortDirection.equalsIgnoreCase("ASC") && !sortDirection.equalsIgnoreCase("DESC"))) {
            sortDirection = "DESC";
        }
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
}

