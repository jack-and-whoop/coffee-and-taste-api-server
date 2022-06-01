package com.jaw.category.ui;

import lombok.Getter;

@Getter
public class CategoryResponseDTO {

    private Long id;
    private String name;

    public CategoryResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
