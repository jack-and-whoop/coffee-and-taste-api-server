package com.jaw.menu.domain;

import java.util.List;
import java.util.Optional;

public interface MenuGroupRepository {

    List<MenuGroup> findAll();

    MenuGroup save(MenuGroup menuGroup);

    Optional<MenuGroup> findById(Long id);

    List<MenuGroup> findAllByCategoryId(Long categoryId);
}
