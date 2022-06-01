package com.jaw.menu.domain;

import java.util.List;
import java.util.Optional;

import com.jaw.menu.ui.MenuResponseDTO;

public interface MenuRepository {

    List<Menu> findAll();

    Menu save(Menu menu);

	List<Menu> findAllByMenuGroupId(Long menuGroupId);

	Optional<Menu> findById(Long id);
}
