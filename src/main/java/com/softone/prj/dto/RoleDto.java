package com.softone.prj.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto {
    private Long id;
    private String name;
    private String code; // ADMIN, USER, MANAGER, GUEST, EDITOR, MODERATOR
    private String description;
    private Integer userCount;
    private List<String> permissions; // READ, WRITE, DELETE, ADMIN
    private String createdAt;
}

