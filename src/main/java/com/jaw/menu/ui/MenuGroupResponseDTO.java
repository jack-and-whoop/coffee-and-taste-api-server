package com.jaw.menu.ui;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuGroupResponseDTO {

    private Long id;
    private String name;
    private String englishName;
    private List<MenuResponseDTO> menus;

    @Builder
    public MenuGroupResponseDTO(Long id, String name, String englishName, List<MenuResponseDTO> menus) {
        this.id = id;
        this.name = name;
        this.englishName = englishName;
        this.menus = menus;
    }
}
