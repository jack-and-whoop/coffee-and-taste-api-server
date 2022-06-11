package com.jaw.order.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuRepository;
import com.jaw.order.domain.Order;
import com.jaw.order.domain.OrderMenu;
import com.jaw.order.domain.OrderRepository;
import com.jaw.order.ui.OrderRequestDTO;
import com.jaw.order.ui.OrderResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final MenuRepository menuRepository;

	public OrderResponseDTO create(OrderRequestDTO request) {
		Long menuId = request.getMenuId();
		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(IllegalArgumentException::new);

		OrderMenu orderMenu = new OrderMenu(menu, request.getQuantity());
		Order order = orderRepository.save(new Order(List.of(orderMenu)));
		return new OrderResponseDTO(order);

	}

	public OrderResponseDTO findById(Long id) {
		Order order = orderRepository.findById(id)
			.orElseThrow(IllegalArgumentException::new);
		return new OrderResponseDTO(order);
	}

	public List<OrderResponseDTO> findAll() {
		return orderRepository.findAll()
			.stream()
			.map(OrderResponseDTO::new)
			.collect(Collectors.toList());
	}
}
