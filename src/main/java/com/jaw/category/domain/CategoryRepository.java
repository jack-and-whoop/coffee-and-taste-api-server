package com.jaw.category.domain;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    List<Category> findAll();

    Category save(Category category);

    Optional<Category> findById(Long id);
}
