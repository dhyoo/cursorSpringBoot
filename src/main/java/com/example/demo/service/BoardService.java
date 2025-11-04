package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.EntityMapper;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final EntityMapper mapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<BoardDto> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(mapper::toBoardDto)
                .collect(Collectors.toList());
    }

    public List<BoardDto> getBoardsByCategory(String category) {
        return boardRepository.findByCategory(category).stream()
                .map(mapper::toBoardDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BoardDto getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("게시글", "id", id));
        // 조회수 증가
        board.setViews(board.getViews() + 1);
        boardRepository.save(board);
        return mapper.toBoardDto(board);
    }

    @Transactional
    public BoardDto createBoard(BoardDto boardDto) {
        Board board = mapper.toBoardEntity(boardDto);
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
        return mapper.toBoardDto(savedBoard);
    }

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
        return mapper.toBoardDto(updatedBoard);
    }

    @Transactional
    public void deleteBoard(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new ResourceNotFoundException("게시글", "id", id);
        }
        boardRepository.deleteById(id);
    }
}
