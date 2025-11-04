package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // User Entity <-> DTO
    public UserDto toUserDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().format(DATE_FORMATTER) : null)
                .lastLogin(user.getLastLogin() != null ? user.getLastLogin().format(DATE_FORMATTER) : null)
                .build();
    }

    public User toUserEntity(UserDto dto) {
        if (dto == null) return null;
        User.UserBuilder builder = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .role(dto.getRole())
                .status(dto.getStatus());
        
        if (dto.getCreatedAt() != null) {
            builder.createdAt(java.time.LocalDate.parse(dto.getCreatedAt(), DATE_FORMATTER));
        }
        if (dto.getLastLogin() != null) {
            builder.lastLogin(java.time.LocalDate.parse(dto.getLastLogin(), DATE_FORMATTER));
        }
        
        return builder.build();
    }

    // Role Entity <-> DTO
    public RoleDto toRoleDto(Role role) {
        if (role == null) return null;
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .code(role.getCode())
                .description(role.getDescription())
                .userCount(role.getUserCount())
                .permissions(role.getPermissions())
                .createdAt(role.getCreatedAt() != null ? role.getCreatedAt().format(DATE_FORMATTER) : null)
                .build();
    }

    public Role toRoleEntity(RoleDto dto) {
        if (dto == null) return null;
        Role.RoleBuilder builder = Role.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .description(dto.getDescription())
                .userCount(dto.getUserCount())
                .permissions(dto.getPermissions());
        
        if (dto.getCreatedAt() != null) {
            builder.createdAt(java.time.LocalDate.parse(dto.getCreatedAt(), DATE_FORMATTER));
        }
        
        return builder.build();
    }

    // Board Entity <-> DTO
    public BoardDto toBoardDto(Board board) {
        if (board == null) return null;
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .authorEmail(board.getAuthorEmail())
                .views(board.getViews())
                .category(board.getCategory())
                .status(board.getStatus())
                .createdAt(board.getCreatedAt() != null ? board.getCreatedAt().format(DATE_FORMATTER) : null)
                .updatedAt(board.getUpdatedAt() != null ? board.getUpdatedAt().format(DATE_FORMATTER) : null)
                .build();
    }

    public Board toBoardEntity(BoardDto dto) {
        if (dto == null) return null;
        Board.BoardBuilder builder = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .authorEmail(dto.getAuthorEmail())
                .views(dto.getViews())
                .category(dto.getCategory())
                .status(dto.getStatus());
        
        if (dto.getCreatedAt() != null) {
            builder.createdAt(java.time.LocalDate.parse(dto.getCreatedAt(), DATE_FORMATTER));
        }
        if (dto.getUpdatedAt() != null) {
            builder.updatedAt(java.time.LocalDate.parse(dto.getUpdatedAt(), DATE_FORMATTER));
        }
        
        return builder.build();
    }

    // Menu Entity <-> DTO
    public MenuItem toMenuItemDto(Menu menu) {
        if (menu == null) return null;
        List<MenuItem> children = menu.getChildren() != null 
                ? menu.getChildren().stream().map(this::toMenuItemDto).collect(Collectors.toList())
                : null;
        
        return MenuItem.builder()
                .menuId(menu.getMenuId())
                .title(menu.getMenuName())
                .path(menu.getMenuPath())
                .icon(menu.getMenuIcon())
                .permission(menu.getPermissionType())
                .children(children != null && !children.isEmpty() ? children : null)
                .build();
    }

    // Activity Entity <-> DTO
    public ActivityDto toActivityDto(Activity activity) {
        if (activity == null) return null;
        return ActivityDto.builder()
                .id(activity.getId())
                .user(activity.getUser())
                .action(activity.getAction())
                .target(activity.getTarget())
                .time(activity.getTime())
                .type(activity.getType())
                .build();
    }
}

