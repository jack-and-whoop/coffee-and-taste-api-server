package com.jaw.order.ui;

import java.math.BigDecimal;

import com.jaw.menu.ui.MenuResponseDTO;
import com.jaw.order.domain.OrderMenu;

import lombok.Getter;

@Getter
public class OrderMenuResponseDTO {

	private final MenuResponseDTO menu;
	private final Long quantity;
	private final BigDecimal price;

	public OrderMenuResponseDTO(OrderMenu orderMenu) {
		this.menu = new MenuResponseDTO(orderMenu.getMenu());
		this.quantity = orderMenu.getQuantity();
		this.price = orderMenu.getPrice();
	}
}
