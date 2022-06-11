package com.jaw.order.ui;

import com.jaw.menu.ui.MenuResponseDTO;
import com.jaw.order.domain.OrderMenu;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderMenuResponseDTO {

	private MenuResponseDTO menu;
	private Long quantity;

	public OrderMenuResponseDTO(OrderMenu orderMenu) {
		this.menu = new MenuResponseDTO(orderMenu.getMenu());
		this.quantity = orderMenu.getQuantity();
	}
}
