package com.jaw.menu.ui;

import com.jaw.menu.domain.MenuGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuGroupRequestDTO {

    private String name;
    private String englishName;
    private String representativeImagePath;

    public MenuGroup toEntity() {
        return MenuGroup.builder()
            .name(name)
            .englishName(englishName)
            .representativeImagePath(representativeImagePath)
            .build();
    }
}
