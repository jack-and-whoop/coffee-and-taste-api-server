package com.jaw.menu.ui;

import com.jaw.menu.domain.MenuGroup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuGroupRequestDTO {

    private String name;
    private String englishName;

    public MenuGroup toEntity() {
        return MenuGroup.builder()
            .name(name)
            .englishName(englishName)
            .build();
    }
}
