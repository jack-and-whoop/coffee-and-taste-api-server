package com.jaw.menu.domain;

import java.util.List;

public interface MenuRepository {

    List<Menu> findAll();

    Menu save(Menu menu);

	List<Menu> findAllByMenuGroupId(Long menuGroupId);
}
