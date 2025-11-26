package com.softone.prj.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 최적화된 페이징 응답
 * 전체 개수는 선택적으로 포함
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "최적화된 페이징 응답")
public class OptimizedPageResponse<T> {
    
    @Schema(description = "현재 페이지 번호 (1부터 시작)")
    private int currentPage;
    
    @Schema(description = "페이지당 항목 수")
    private int pageSize;
    
    @Schema(description = "전체 항목 수 (needTotalCount=false면 null)")
    private Long totalElements;
    
    @Schema(description = "전체 페이지 수 (needTotalCount=false면 null)")
    private Integer totalPages;
    
    @Schema(description = "다음 페이지 존재 여부")
    private boolean hasNext;
    
    @Schema(description = "이전 페이지 존재 여부")
    private boolean hasPrevious;
    
    @Schema(description = "데이터 목록")
    private List<T> content;
    
    /**
     * COUNT 없이 생성 (성능 최적화)
     */
    public static <T> OptimizedPageResponse<T> withoutCount(List<T> content, OptimizedPageRequest request) {
        request.validate();
        
        // size+1개를 조회하여 다음 페이지 존재 여부만 확인
        boolean hasNext = content.size() > request.getSize();
        List<T> actualContent = hasNext ? content.subList(0, request.getSize()) : content;
        
        return OptimizedPageResponse.<T>builder()
                .currentPage(request.getPage())
                .pageSize(request.getSize())
                .totalElements(null) // COUNT 생략
                .totalPages(null)    // COUNT 생략
                .hasNext(hasNext)
                .hasPrevious(request.getPage() > 1)
                .content(actualContent)
                .build();
    }
    
    /**
     * COUNT 포함하여 생성 (전통적 방식)
     */
    public static <T> OptimizedPageResponse<T> withCount(List<T> content, long totalElements, OptimizedPageRequest request) {
        request.validate();
        
        int totalPages = (int) Math.ceil((double) totalElements / request.getSize());
        
        return OptimizedPageResponse.<T>builder()
                .currentPage(request.getPage())
                .pageSize(request.getSize())
                .totalElements(totalElements)
                .totalPages(totalPages)
                .hasNext(request.getPage() < totalPages)
                .hasPrevious(request.getPage() > 1)
                .content(content)
                .build();
    }
}

