package com.jaw.order.ui;

import java.util.List;
import java.util.stream.Collectors;

import com.jaw.order.domain.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseDTO {

	private Long id;
	private List<OrderMenuResponseDTO> orderMenus;

	public OrderResponseDTO(Order order) {
		this.id = order.getId();
		this.orderMenus = order.getOrderMenus()
			.stream()
			.map(OrderMenuResponseDTO::new)
			.collect(Collectors.toList());
	}
}
