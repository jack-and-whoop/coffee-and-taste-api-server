package com.jaw.menu.ui;

import com.jaw.menu.domain.MenuGroup;
import lombok.Getter;

@Getter
public class MenuGroupResponseDTO {

    private Long id;
    private String name;
    private String englishName;

    public MenuGroupResponseDTO(MenuGroup menuGroup) {
        this.id = menuGroup.getId();
        this.name = menuGroup.getName();
        this.englishName = menuGroup.getEnglishName();
    }
}
