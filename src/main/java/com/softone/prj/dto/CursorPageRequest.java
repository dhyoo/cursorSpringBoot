package com.softone.prj.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 커서 기반 페이징 요청 (COUNT 쿼리 불필요)
 * 대용량 데이터에 적합
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "커서 기반 페이징 요청 (성능 최적화)")
public class CursorPageRequest {
    
    @Schema(description = "마지막으로 조회한 항목의 ID (다음 페이지 조회 시)", example = "10")
    private Long cursor;
    
    @Builder.Default
    @Schema(description = "페이지당 항목 수", example = "10", defaultValue = "10")
    private int size = 10;
    
    @Schema(description = "정렬 기준 필드", example = "createdAt")
    private String sortBy;
    
    @Builder.Default
    @Schema(description = "정렬 방향 (ASC, DESC)", example = "DESC", defaultValue = "DESC")
    private String sortDirection = "DESC";
    
    /**
     * 유효성 검증
     */
    public void validate() {
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

