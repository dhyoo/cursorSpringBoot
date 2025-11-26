package com.softone.prj.service;

import com.softone.prj.dto.MenuItem;
import com.softone.prj.entity.Menu;
import com.softone.prj.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public List<MenuItem> getMenus() {
        // 최상위 메뉴만 조회
        List<Menu> topMenus = menuRepository.findByParentMenuIdIsNull();
        
        return topMenus.stream()
                .map(this::convertMenuToMenuItem)
                .collect(Collectors.toList());
    }
    
    private MenuItem convertMenuToMenuItem(Menu menu) {
        // 하위 메뉴 조회
        List<Menu> children = menuRepository.findByParentMenuId(menu.getMenuId());
        
        MenuItem item = MenuItem.builder()
                .menuId(menu.getMenuId())
                .title(menu.getMenuName())
                .path(menu.getMenuPath())
                .icon(menu.getMenuIcon())
                .permission(menu.getPermissionType())
                .build();
        
        // 하위 메뉴가 있으면 재귀적으로 변환
        if (!children.isEmpty()) {
            List<MenuItem> childItems = children.stream()
                    .map(this::convertMenuToMenuItem)
                    .collect(Collectors.toList());
            item.setChildren(childItems);
        }
        
        return item;
    }
}
