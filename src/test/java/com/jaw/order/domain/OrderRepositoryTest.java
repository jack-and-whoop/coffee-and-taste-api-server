package com.jaw.order.domain;

import static com.jaw.Fixtures.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaw.member.domain.Member;
import com.jaw.menu.domain.Menu;
import com.jaw.order.application.InMemoryOrderMenuRepository;
import com.jaw.order.application.InMemoryOrderRepository;

class OrderRepositoryTest {

	private OrderRepository orderRepository;
	private OrderMenuRepository orderMenuRepository;

	@BeforeEach
	void setUp() {
		orderRepository = new InMemoryOrderRepository();
		orderMenuRepository = new InMemoryOrderMenuRepository();
	}

	@DisplayName("새로운 주문을 생성한다.")
	@Test
	void create() {
		Menu menu = Menu.builder().build();
		OrderMenu orderMenu = orderMenuRepository.save(new OrderMenu(menu, 1L));
		Member member = member();
		Order orderRequest = new Order(member);
		orderRequest.addOrderMenu(orderMenu);

		Order order = orderRepository.save(orderRequest);

		assertThat(order.getMember()).isEqualTo(member);
		assertThat(order.getOrderMenus()).contains(orderMenu);
		assertThat(order.getOrderMenus().get(0).getOrder()).isEqualTo(orderRequest);
	}
}
