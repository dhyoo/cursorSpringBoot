package com.softone.prj.mapper;

import com.softone.prj.dto.BoardDto;
import com.softone.prj.dto.CursorPageRequest;
import com.softone.prj.dto.OptimizedPageRequest;
import com.softone.prj.dto.PageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    
    /**
     * 게시글 목록 조회 (페이징)
     */
    List<BoardDto> selectBoardsWithPaging(PageRequest pageRequest);
    
    /**
     * 게시글 전체 개수 조회
     */
    long countBoards();
    
    /**
     * 카테고리별 게시글 목록 조회 (페이징)
     */
    List<BoardDto> selectBoardsByCategoryWithPaging(
            @Param("category") String category,
            @Param("pageRequest") PageRequest pageRequest
    );
    
    /**
     * 카테고리별 게시글 개수 조회
     */
    long countBoardsByCategory(@Param("category") String category);
    
    /**
     * 검색어로 게시글 목록 조회 (페이징)
     */
    List<BoardDto> searchBoardsWithPaging(
            @Param("keyword") String keyword,
            @Param("pageRequest") PageRequest pageRequest
    );
    
    /**
     * 검색어로 게시글 개수 조회
     */
    long countSearchBoards(@Param("keyword") String keyword);
    
    // ========== 성능 최적화 메서드 ==========
    
    /**
     * 커서 기반 페이징 (COUNT 쿼리 없음)
     * size+1개를 조회하여 다음 페이지 존재 여부만 확인
     */
    List<BoardDto> selectBoardsWithCursor(CursorPageRequest request);
    
    /**
     * 최적화된 페이징 (선택적 COUNT)
     * size+1개를 조회하여 hasNext 판단
     */
    List<BoardDto> selectBoardsOptimized(OptimizedPageRequest request);
}


