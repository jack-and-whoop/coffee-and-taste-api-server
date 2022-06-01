package com.jaw.category.ui;

import java.util.List;

import com.jaw.menu.ui.MenuGroupResponseDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryMenuGroupsResponseDTO extends CategoryResponseDTO {

	private List<MenuGroupResponseDTO> menuGroups;

	@Builder
	public CategoryMenuGroupsResponseDTO(Long id, String name, List<MenuGroupResponseDTO> menuGroups) {
		super(id, name);
		this.menuGroups = menuGroups;
	}
}
