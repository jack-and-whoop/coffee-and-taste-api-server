package com.jaw.cart.ui;

import java.util.List;
import java.util.stream.Collectors;

import com.jaw.order.domain.Order;
import com.jaw.order.ui.OrderMenuResponseDTO;

import lombok.Getter;

@Getter
public class CartMenuOrderResponseDTO {

	private final Long id;
	private final List<OrderMenuResponseDTO> orderMenus;

	public CartMenuOrderResponseDTO(Order order) {
		this.id = order.getId();
		this.orderMenus = order.getOrderMenus()
			.stream()
			.map(OrderMenuResponseDTO::new)
			.collect(Collectors.toList());
	}
}
