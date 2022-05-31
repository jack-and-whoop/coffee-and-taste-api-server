package com.jaw.category.ui;

import com.jaw.menu.ui.MenuGroupResponseDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryResponseDTO {

    private Long id;
    private String name;
    private List<MenuGroupResponseDTO> menuGroups;

    public CategoryResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryResponseDTO(Long id, String name, List<MenuGroupResponseDTO> menuGroups) {
        this.id = id;
        this.name = name;
        this.menuGroups = menuGroups;
    }
}
