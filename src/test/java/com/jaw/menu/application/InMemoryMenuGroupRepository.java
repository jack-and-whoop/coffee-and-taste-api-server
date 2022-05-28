package com.jaw.menu.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void clear() {
        menuGroups.clear();
    }
}
