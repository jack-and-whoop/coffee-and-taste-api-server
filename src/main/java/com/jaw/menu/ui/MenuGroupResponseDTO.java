package com.jaw.menu.ui;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuGroupResponseDTO {

    private Long id;
    private String name;

    public MenuGroupResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
