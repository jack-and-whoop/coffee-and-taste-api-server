package com.jaw.menu.ui;

import com.jaw.menu.domain.MenuGroup;
import lombok.Getter;

@Getter
public class MenuGroupResponseDTO {

    private final Long id;
    private final String name;
    private final String englishName;
    private final String representativeImagePath;

    public MenuGroupResponseDTO(MenuGroup menuGroup) {
        this.id = menuGroup.getId();
        this.name = menuGroup.getName();
        this.englishName = menuGroup.getEnglishName();
        this.representativeImagePath = menuGroup.getRepresentativeImagePath();
    }
}
