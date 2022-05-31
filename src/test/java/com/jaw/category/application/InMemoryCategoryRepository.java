package com.jaw.category.application;

import java.util.*;

import com.jaw.category.domain.Category;
import com.jaw.category.domain.CategoryRepository;

public class InMemoryCategoryRepository implements CategoryRepository {

    private final Map<Long, Category> categories = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

    @Override
    public Category save(Category category) {
        category.setId(++sequence);
        categories.put(category.getId(), category);
        return category;
    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(categories.get(id));
    }

    public void clear() {
        sequence = 0;
        categories.clear();
    }
}
