package com.jaw.order.ui;

import com.jaw.menu.ui.MenuResponseDTO;
import com.jaw.order.domain.OrderMenu;

import lombok.Getter;

@Getter
public class OrderMenuResponseDTO {

	private final MenuResponseDTO menu;
	private final Long quantity;

	public OrderMenuResponseDTO(OrderMenu orderMenu) {
		this.menu = new MenuResponseDTO(orderMenu.getMenu());
		this.quantity = orderMenu.getQuantity();
	}
}
