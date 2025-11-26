package com.softone.prj.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 커서 기반 페이징 응답
 * COUNT 쿼리 없이 다음 페이지 존재 여부만 확인
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "커서 기반 페이징 응답 (성능 최적화)")
public class CursorPageResponse<T> {
    
    @Schema(description = "데이터 목록")
    private List<T> content;
    
    @Schema(description = "다음 페이지 커서 (다음 요청 시 사용)")
    private Long nextCursor;
    
    @Schema(description = "다음 페이지 존재 여부")
    private boolean hasNext;
    
    @Schema(description = "페이지 크기")
    private int size;
    
    /**
     * 커서 기반 응답 생성
     * size+1개를 조회하여 다음 페이지 존재 여부 확인
     */
    public static <T> CursorPageResponse<T> of(List<T> allContent, int requestedSize, java.util.function.Function<T, Long> idExtractor) {
        boolean hasNext = allContent.size() > requestedSize;
        List<T> content = hasNext ? allContent.subList(0, requestedSize) : allContent;
        
        Long nextCursor = null;
        if (hasNext && !content.isEmpty()) {
            nextCursor = idExtractor.apply(content.get(content.size() - 1));
        }
        
        return CursorPageResponse.<T>builder()
                .content(content)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .size(content.size())
                .build();
    }
}

