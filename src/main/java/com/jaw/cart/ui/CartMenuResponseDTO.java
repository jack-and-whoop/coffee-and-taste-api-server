package com.jaw.cart.ui;

import com.jaw.cart.domain.CartMenu;
import com.jaw.menu.ui.MenuResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartMenuResponseDTO {

	private MenuResponseDTO menu;
	private long count;

	public CartMenuResponseDTO(CartMenu cartMenu) {
		this.menu = new MenuResponseDTO(cartMenu.getMenu());
		this.count = cartMenu.getCount();
	}
}
