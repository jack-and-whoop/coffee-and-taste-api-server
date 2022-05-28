package com.jaw.menu.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void clear() {
        menus.clear();
    }
}
