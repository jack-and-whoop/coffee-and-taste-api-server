package com.jaw.category.ui;

import java.util.List;
import java.util.stream.Collectors;

import com.jaw.category.domain.Category;
import com.jaw.menu.domain.MenuGroup;
import com.jaw.menu.ui.MenuGroupResponseDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryMenuGroupsResponseDTO extends CategoryResponseDTO {

	private List<MenuGroupResponseDTO> menuGroups;

	@Builder
	public CategoryMenuGroupsResponseDTO(Category category, List<MenuGroup> menuGroups) {
		super(category);
		this.menuGroups = menuGroups.stream()
			.map(MenuGroupResponseDTO::new)
			.collect(Collectors.toList());
	}
}
