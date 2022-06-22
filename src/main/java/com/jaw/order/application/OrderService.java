package com.jaw.order.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;
import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuRepository;
import com.jaw.order.domain.Order;
import com.jaw.order.domain.OrderMenu;
import com.jaw.order.domain.OrderMenuRepository;
import com.jaw.order.domain.OrderRepository;
import com.jaw.order.ui.OrderRequestDTO;
import com.jaw.order.ui.OrderResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderMenuRepository orderMenuRepository;
	private final MenuRepository menuRepository;
	private final MemberRepository memberRepository;

	public OrderResponseDTO create(OrderRequestDTO request, Long userId) {
		Member member = memberRepository.findById(userId)
			.orElseThrow(IllegalArgumentException::new);

		Menu menu = menuRepository.findById(request.getMenuId())
			.orElseThrow(IllegalArgumentException::new);

		OrderMenu orderMenu = orderMenuRepository.save(new OrderMenu(menu, request.getQuantity()));

		Order order = new Order(member);
		order.addOrderMenu(orderMenu);

		return new OrderResponseDTO(orderRepository.save(order));
	}

	@Transactional(readOnly = true)
	public OrderResponseDTO findById(Long id) {
		Order order = orderRepository.findById(id)
			.orElseThrow(IllegalArgumentException::new);
		return new OrderResponseDTO(order);
	}

	@Transactional(readOnly = true)
	public List<OrderResponseDTO> findAll() {
		return orderRepository.findAll()
			.stream()
			.map(OrderResponseDTO::new)
			.collect(Collectors.toList());
	}
}
