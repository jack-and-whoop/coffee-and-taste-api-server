package com.jaw.menu.ui;

import java.util.List;

import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuGroup;

import lombok.Getter;

@Getter
public class MenuGroupMenusResponseDTO extends MenuGroupResponseDTO {

	private List<MenuResponseDTO> menus;

	public MenuGroupMenusResponseDTO(MenuGroup menuGroup, List<Menu> menus) {
		super(menuGroup);
		this.menus = menus.stream()
			.map(MenuResponseDTO::new)
			.collect(java.util.stream.Collectors.toList());
	}
}
