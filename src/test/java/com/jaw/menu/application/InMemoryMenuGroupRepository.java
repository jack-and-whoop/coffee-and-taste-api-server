package com.jaw.menu.application;

import java.util.*;
import java.util.stream.Collectors;

import com.jaw.menu.domain.MenuGroup;
import com.jaw.menu.domain.MenuGroupRepository;

public class InMemoryMenuGroupRepository implements MenuGroupRepository {

    private final Map<Long, MenuGroup> menuGroups = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public List<MenuGroup> findAll() {
        return new ArrayList<>(menuGroups.values());
    }

    @Override
    public MenuGroup save(MenuGroup menuGroup) {
        menuGroup.setId(++sequence);
        menuGroups.put(menuGroup.getId(), menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<MenuGroup> findById(Long id) {
        return Optional.ofNullable(menuGroups.get(id));
    }

    @Override
    public List<MenuGroup> findAllByCategoryId(Long categoryId) {
        return menuGroups.values()
            .stream()
            .filter(menuGroup -> menuGroup.getCategory().getId().equals(categoryId))
            .collect(Collectors.toList());
    }

    public void clear() {
        sequence = 0;
        menuGroups.clear();
    }
}
