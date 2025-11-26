package com.softone.prj.repository;

import com.softone.prj.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    
    /**
     * 카테고리로 게시글 조회
     */
    List<Board> findByCategory(String category);
    
    /**
     * 카테고리로 게시글 조회 (페이징)
     */
    Page<Board> findByCategoryOrderByCreatedAtDesc(String category, Pageable pageable);
    
    /**
     * 제목으로 게시글 검색 (페이징)
     */
    Page<Board> findByTitleContainingOrderByCreatedAtDesc(String title, Pageable pageable);
    
    /**
     * 내용으로 게시글 검색 (페이징)
     */
    Page<Board> findByContentContainingOrderByCreatedAtDesc(String content, Pageable pageable);
    
    /**
     * 제목 또는 내용으로 검색 (페이징)
     */
    Page<Board> findByTitleContainingOrContentContainingOrderByCreatedAtDesc(
            String title, String content, Pageable pageable);
}
