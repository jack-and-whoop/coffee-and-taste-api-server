package com.jaw.category.application;

import com.jaw.category.domain.Category;
import com.jaw.category.domain.CategoryRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
