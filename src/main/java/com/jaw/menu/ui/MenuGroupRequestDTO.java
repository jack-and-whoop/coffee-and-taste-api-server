package com.jaw.menu.ui;

import com.jaw.menu.domain.MenuGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuGroupRequestDTO {

    private String name;
    private String englishName;

    public MenuGroupRequestDTO(String name) {
        this.name = name;
    }

    public MenuGroup toEntity() {
        return MenuGroup.builder()
            .name(name)
            .englishName(englishName)
            .build();
    }
}
