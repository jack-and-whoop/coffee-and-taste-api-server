package com.jaw.cart.ui;

import com.jaw.cart.domain.CartMenu;
import com.jaw.menu.ui.MenuResponseDTO;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CartMenuResponseDTO {

	private final Long id;
	private final MenuResponseDTO menu;
	private final Long quantity;
	private final BigDecimal price;

	public CartMenuResponseDTO(CartMenu cartMenu) {
		this.id = cartMenu.getId();
		this.menu = new MenuResponseDTO(cartMenu.getMenu());
		this.quantity = cartMenu.getQuantity();
		this.price = cartMenu.getPrice();
	}
}
