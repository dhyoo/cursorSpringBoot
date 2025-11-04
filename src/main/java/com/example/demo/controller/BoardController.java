package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.BoardDto;
import com.example.demo.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Tag(name = "Board API", description = "게시판 관리 API")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시글 목록 조회", description = "모든 게시글 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BoardDto>>> getAllBoards(
            @Parameter(description = "카테고리 필터 (선택사항)") @RequestParam(required = false) String category) {
        List<BoardDto> boards = category != null && !category.isEmpty()
                ? boardService.getBoardsByCategory(category)
                : boardService.getAllBoards();
        return ResponseEntity.ok(ApiResponse.success(boards));
    }

    @Operation(summary = "게시글 조회", description = "ID로 특정 게시글을 조회합니다. 조회 시 조회수가 증가합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardDto>> getBoardById(
            @Parameter(description = "게시글 ID") @PathVariable Long id) {
        BoardDto board = boardService.getBoardById(id);
        return ResponseEntity.ok(ApiResponse.success(board));
    }

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<BoardDto>> createBoard(@RequestBody BoardDto boardDto) {
        BoardDto createdBoard = boardService.createBoard(boardDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdBoard, "게시글이 생성되었습니다."));
    }

    @Operation(summary = "게시글 수정", description = "기존 게시글 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardDto>> updateBoard(
            @Parameter(description = "게시글 ID") @PathVariable Long id,
            @RequestBody BoardDto boardDto) {
        BoardDto board = boardService.updateBoard(id, boardDto);
        return ResponseEntity.ok(ApiResponse.success(board, "게시글이 수정되었습니다."));
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(
            @Parameter(description = "게시글 ID") @PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok(ApiResponse.success(null, "게시글이 삭제되었습니다."));
    }
}
