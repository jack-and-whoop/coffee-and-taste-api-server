package com.jaw.category.ui;

import com.jaw.category.domain.Category;

import lombok.Getter;

@Getter
public class CategoryResponseDTO {

    private final Long id;
    private final String name;

    public CategoryResponseDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
