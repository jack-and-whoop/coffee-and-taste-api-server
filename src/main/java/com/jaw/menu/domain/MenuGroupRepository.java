package com.jaw.menu.domain;

import java.util.List;

public interface MenuGroupRepository {

    List<MenuGroup> findAll();

    MenuGroup save(MenuGroup menuGroup);
}
