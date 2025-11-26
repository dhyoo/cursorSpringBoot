package com.softone.prj.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "페이징 응답")
public class PageResponse<T> {
    
    @Schema(description = "현재 페이지 번호 (1부터 시작)")
    private int currentPage;
    
    @Schema(description = "페이지당 항목 수")
    private int pageSize;
    
    @Schema(description = "전체 항목 수")
    private long totalElements;
    
    @Schema(description = "전체 페이지 수")
    private int totalPages;
    
    @Schema(description = "첫 페이지 여부")
    private boolean first;
    
    @Schema(description = "마지막 페이지 여부")
    private boolean last;
    
    @Schema(description = "비어있음 여부")
    private boolean empty;
    
    @Schema(description = "데이터 목록")
    private List<T> content;
    
    /**
     * JPA Page 객체로부터 PageResponse 생성
     */
    public static <T> PageResponse<T> of(Page<T> page) {
        return PageResponse.<T>builder()
                .currentPage(page.getNumber() + 1) // JPA는 0부터 시작하므로 +1
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .content(page.getContent())
                .build();
    }
    
    /**
     * MyBatis 결과로부터 PageResponse 생성
     */
    public static <T> PageResponse<T> of(List<T> content, long totalElements, PageRequest pageRequest) {
        pageRequest.validate();
        
        int totalPages = (int) Math.ceil((double) totalElements / pageRequest.getSize());
        int currentPage = pageRequest.getPage();
        
        return PageResponse.<T>builder()
                .currentPage(currentPage)
                .pageSize(pageRequest.getSize())
                .totalElements(totalElements)
                .totalPages(totalPages)
                .first(currentPage == 1)
                .last(currentPage >= totalPages)
                .empty(content.isEmpty())
                .content(content)
                .build();
    }
    
    /**
     * 빈 페이지 응답 생성
     */
    public static <T> PageResponse<T> empty(PageRequest pageRequest) {
        pageRequest.validate();
        
        return PageResponse.<T>builder()
                .currentPage(pageRequest.getPage())
                .pageSize(pageRequest.getSize())
                .totalElements(0)
                .totalPages(0)
                .first(true)
                .last(true)
                .empty(true)
                .content(List.of())
                .build();
    }
}


