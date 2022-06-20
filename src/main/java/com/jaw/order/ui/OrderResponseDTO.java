package com.jaw.order.ui;

import com.jaw.order.domain.Order;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponseDTO {

	private final Long id;
	private final List<OrderMenuResponseDTO> orderMenus;

	public OrderResponseDTO(Order order) {
		this.id = order.getId();
		this.orderMenus = order.getOrderMenus()
			.stream()
			.map(OrderMenuResponseDTO::new)
			.collect(Collectors.toList());
	}
}
