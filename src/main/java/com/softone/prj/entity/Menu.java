package com.softone.prj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menus")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @Column(name = "menu_id", length = 50)
    private String menuId;

    @Column(name = "menu_nm", nullable = false, length = 100)
    private String menuName;

    @Column(name = "menu_path", length = 200)
    private String menuPath;

    @Column(name = "menu_icon", length = 100)
    private String menuIcon;

    @Column(name = "parent_menu_id", length = 50)
    private String parentMenuId;

    @Column(name = "menu_order")
    private Integer menuOrder;

    @Column(name = "menu_level")
    private Integer menuLevel;

    @Column(name = "is_active", length = 1)
    @Builder.Default
    private String isActive = "Y";

    @Column(name = "is_visible", length = 1)
    @Builder.Default
    private String isVisible = "Y";

    @Column(length = 500)
    private String description;

    @Column(name = "permission_type", length = 20)
    private String permissionType; // READ, WRITE, DELETE, ADMIN

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_menu_id", insertable = false, updatable = false)
    private Menu parentMenu;

    @OneToMany(mappedBy = "parentMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Menu> children = new ArrayList<>();
}

