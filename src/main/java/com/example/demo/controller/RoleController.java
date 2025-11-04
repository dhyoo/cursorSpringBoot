package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.RoleDto;
import com.example.demo.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Role API", description = "권한(역할) 관리 API")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "역할 목록 조회", description = "모든 역할 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleDto>>> getAllRoles() {
        return ResponseEntity.ok(ApiResponse.success(roleService.getAllRoles()));
    }

    @Operation(summary = "역할 조회", description = "ID로 특정 역할을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleDto>> getRoleById(
            @Parameter(description = "역할 ID") @PathVariable Long id) {
        RoleDto role = roleService.getRoleById(id);
        return ResponseEntity.ok(ApiResponse.success(role));
    }

    @Operation(summary = "역할 생성", description = "새로운 역할을 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<RoleDto>> createRole(@RequestBody RoleDto roleDto) {
        RoleDto createdRole = roleService.createRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdRole, "역할이 생성되었습니다."));
    }

    @Operation(summary = "역할 수정", description = "기존 역할 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleDto>> updateRole(
            @Parameter(description = "역할 ID") @PathVariable Long id,
            @RequestBody RoleDto roleDto) {
        RoleDto role = roleService.updateRole(id, roleDto);
        return ResponseEntity.ok(ApiResponse.success(role, "역할이 수정되었습니다."));
    }

    @Operation(summary = "역할 삭제", description = "역할을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(
            @Parameter(description = "역할 ID") @PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(ApiResponse.success(null, "역할이 삭제되었습니다."));
    }
}
