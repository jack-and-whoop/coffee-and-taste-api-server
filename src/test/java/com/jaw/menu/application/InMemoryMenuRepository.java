package com.jaw.menu.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuRepository;

public class InMemoryMenuRepository implements MenuRepository {

    private final Map<Long, Menu> menus = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public Menu save(Menu menu) {
        menu.setId(++sequence);
        menus.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public List<Menu> findAllByMenuGroupId(Long menuGroupId) {
        return menus.values().stream()
            .filter(menu -> menu.getMenuGroup().getId().equals(menuGroupId))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Menu> findById(Long id) {
        return Optional.ofNullable(menus.get(id));
    }

    public void clear() {
        sequence = 0;
        menus.clear();
    }
}
