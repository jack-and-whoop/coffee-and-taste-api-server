package com.jaw.menu.ui;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuGroupResponseDTO {

    private Long id;
    private String name;
    private String englishName;

    public MenuGroupResponseDTO(Long id, String name, String englishName) {
        this.id = id;
        this.name = name;
        this.englishName = englishName;
    }
}
