package com.jaw.category.ui;

import com.jaw.category.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequestDTO {

    private String name;

    public CategoryRequestDTO(String name) {
        this.name = name;
    }

    public Category toEntity() {
        return new Category(name);
    }
}
