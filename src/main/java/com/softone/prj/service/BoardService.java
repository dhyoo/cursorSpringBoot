package com.softone.prj.service;

import com.softone.prj.dto.*;
import com.softone.prj.entity.Board;
import com.softone.prj.exception.ResourceNotFoundException;
import com.softone.prj.mapper.BoardMapper;
import com.softone.prj.mapper.EntityMapper;
import com.softone.prj.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper; // MyBatis Mapper
    private final EntityMapper entityMapper;

    /**
     * 모든 게시글 조회 (페이징 없음)
     */
    public List<BoardDto> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(entityMapper::toBoardDto)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리별 게시글 조회 (페이징 없음)
     */
    public List<BoardDto> getBoardsByCategory(String category) {
        return boardRepository.findByCategory(category).stream()
                .map(entityMapper::toBoardDto)
                .collect(Collectors.toList());
    }

    /**
     * JPA를 사용한 페이징 조회
     */
    @Transactional(readOnly = true)
    public PageResponse<BoardDto> getBoardsWithPagingJPA(PageRequest pageRequest) {
        pageRequest.validate();
        
        Page<Board> boardPage = boardRepository.findAll(pageRequest.toPageable());
        Page<BoardDto> dtoPage = boardPage.map(entityMapper::toBoardDto);
        
        log.debug("JPA 페이징 조회: page={}, size={}, total={}", 
                pageRequest.getPage(), pageRequest.getSize(), dtoPage.getTotalElements());
        
        return PageResponse.of(dtoPage);
    }

    /**
     * MyBatis를 사용한 페이징 조회
     */
    @Transactional(readOnly = true)
    public PageResponse<BoardDto> getBoardsWithPagingMyBatis(PageRequest pageRequest) {
        pageRequest.validate();
        
        List<BoardDto> boards = boardMapper.selectBoardsWithPaging(pageRequest);
        long totalElements = boardMapper.countBoards();
        
        log.debug("MyBatis 페이징 조회: page={}, size={}, total={}", 
                pageRequest.getPage(), pageRequest.getSize(), totalElements);
        
        return PageResponse.of(boards, totalElements, pageRequest);
    }

    /**
     * JPA를 사용한 카테고리별 페이징 조회
     */
    @Transactional(readOnly = true)
    public PageResponse<BoardDto> getBoardsByCategoryWithPagingJPA(String category, PageRequest pageRequest) {
        pageRequest.validate();
        
        Page<Board> boardPage = boardRepository.findByCategoryOrderByCreatedAtDesc(
                category, pageRequest.toPageable());
        Page<BoardDto> dtoPage = boardPage.map(entityMapper::toBoardDto);
        
        return PageResponse.of(dtoPage);
    }

    /**
     * MyBatis를 사용한 카테고리별 페이징 조회
     */
    @Transactional(readOnly = true)
    public PageResponse<BoardDto> getBoardsByCategoryWithPagingMyBatis(String category, PageRequest pageRequest) {
        pageRequest.validate();
        
        List<BoardDto> boards = boardMapper.selectBoardsByCategoryWithPaging(category, pageRequest);
        long totalElements = boardMapper.countBoardsByCategory(category);
        
        return PageResponse.of(boards, totalElements, pageRequest);
    }

    /**
     * MyBatis를 사용한 검색 페이징
     */
    @Transactional(readOnly = true)
    public PageResponse<BoardDto> searchBoardsWithPaging(String keyword, PageRequest pageRequest) {
        pageRequest.validate();
        
        List<BoardDto> boards = boardMapper.searchBoardsWithPaging(keyword, pageRequest);
        long totalElements = boardMapper.countSearchBoards(keyword);
        
        log.debug("게시글 검색: keyword='{}', page={}, size={}, total={}", 
                keyword, pageRequest.getPage(), pageRequest.getSize(), totalElements);
        
        return PageResponse.of(boards, totalElements, pageRequest);
    }

    /**
     * ID로 게시글 조회
     */
    @Transactional
    public BoardDto getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("게시글", "id", id));
        
        // 조회수 증가
        board.setViews(board.getViews() + 1);
        boardRepository.save(board);
        
        return entityMapper.toBoardDto(board);
    }

    /**
     * 게시글 생성
     */
    @Transactional
    public BoardDto createBoard(BoardDto boardDto) {
        Board board = entityMapper.toBoardEntity(boardDto);
        if (board.getViews() == null) {
            board.setViews(0L);
        }
        if (board.getCategory() == null) {
            board.setCategory("일반");
        }
        if (board.getStatus() == null) {
            board.setStatus("공개");
        }
        LocalDate now = LocalDate.now();
        if (board.getCreatedAt() == null) {
            board.setCreatedAt(now);
        }
        if (board.getUpdatedAt() == null) {
            board.setUpdatedAt(now);
        }
        Board savedBoard = boardRepository.save(board);
        return entityMapper.toBoardDto(savedBoard);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public BoardDto updateBoard(Long id, BoardDto boardDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("게시글", "id", id));
        
        if (boardDto.getTitle() != null) board.setTitle(boardDto.getTitle());
        if (boardDto.getContent() != null) board.setContent(boardDto.getContent());
        if (boardDto.getCategory() != null) board.setCategory(boardDto.getCategory());
        if (boardDto.getStatus() != null) board.setStatus(boardDto.getStatus());
        
        board.setUpdatedAt(LocalDate.now());
        Board updatedBoard = boardRepository.save(board);
        
        return entityMapper.toBoardDto(updatedBoard);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deleteBoard(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new ResourceNotFoundException("게시글", "id", id);
        }
        boardRepository.deleteById(id);
    }
    
    // ========== 성능 최적화 메서드 ==========
    
    /**
     * 커서 기반 페이징 (COUNT 쿼리 없음)
     * - 대용량 데이터에 최적화
     * - 전체 개수 조회하지 않음
     * - 다음 페이지 존재 여부만 확인
     */
    @Transactional(readOnly = true)
    public CursorPageResponse<BoardDto> getBoardsWithCursor(CursorPageRequest request) {
        request.validate();
        
        // size+1개를 조회하여 다음 페이지 존재 여부 확인
        List<BoardDto> boards = boardMapper.selectBoardsWithCursor(request);
        
        log.debug("커서 기반 페이징: cursor={}, size={}, 조회 결과={}", 
                request.getCursor(), request.getSize(), boards.size());
        
        return CursorPageResponse.of(boards, request.getSize(), BoardDto::getId);
    }
    
    /**
     * 최적화된 페이징 (선택적 COUNT)
     * - needTotalCount=true: 전통적 페이징 (COUNT 포함)
     * - needTotalCount=false: 빠른 페이징 (COUNT 생략)
     */
    @Transactional(readOnly = true)
    public OptimizedPageResponse<BoardDto> getBoardsOptimized(OptimizedPageRequest request) {
        request.validate();
        
        if (request.isNeedTotalCount()) {
            // 전체 개수 포함 (전통적 방식)
            List<BoardDto> boards = boardMapper.selectBoardsOptimized(request);
            long totalElements = boardMapper.countBoards();
            
            log.debug("최적화 페이징 (COUNT 포함): page={}, size={}, total={}", 
                    request.getPage(), request.getSize(), totalElements);
            
            return OptimizedPageResponse.withCount(boards, totalElements, request);
        } else {
            // 전체 개수 생략 (성능 최적화)
            // size+1개를 조회하여 hasNext만 판단
            List<BoardDto> boards = boardMapper.selectBoardsOptimized(request);
            
            log.debug("최적화 페이징 (COUNT 생략): page={}, size={}, 조회 결과={}", 
                    request.getPage(), request.getSize(), boards.size());
            
            return OptimizedPageResponse.withoutCount(boards, request);
        }
    }
}
