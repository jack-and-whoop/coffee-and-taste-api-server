package com.jaw.category.domain;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll();

    Category save(Category category);
}
