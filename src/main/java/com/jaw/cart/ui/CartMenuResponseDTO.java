package com.jaw.cart.ui;

import com.jaw.cart.domain.CartMenu;
import com.jaw.menu.ui.MenuResponseDTO;

import lombok.Getter;

@Getter
public class CartMenuResponseDTO {

	private final Long id;
	private final MenuResponseDTO menu;
	private final long count;

	public CartMenuResponseDTO(CartMenu cartMenu) {
		this.id = cartMenu.getId();
		this.menu = new MenuResponseDTO(cartMenu.getMenu());
		this.count = cartMenu.getCount();
	}
}
