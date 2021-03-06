package com.jaw.order.application;

import static com.jaw.Fixtures.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaw.member.application.InMemoryMemberRepository;
import com.jaw.menu.application.InMemoryMenuRepository;
import com.jaw.menu.domain.Menu;
import com.jaw.menu.ui.MenuResponseDTO;
import com.jaw.order.ui.OrderRequestDTO;
import com.jaw.order.ui.OrderResponseDTO;

class OrderServiceTest {

	private InMemoryOrderRepository orderRepository;
	private InMemoryMenuRepository menuRepository;
	private InMemoryOrderMenuRepository orderMenuRepository;
	private InMemoryMemberRepository memberRepository;
	private OrderService orderService;

	private Menu coldBrew;
	private Menu icedCoffee;

	@BeforeEach
	void setup() {
		orderRepository = new InMemoryOrderRepository();
		menuRepository = new InMemoryMenuRepository();
		orderMenuRepository = new InMemoryOrderMenuRepository();
		memberRepository = new InMemoryMemberRepository();
		orderService = new OrderService(orderRepository, orderMenuRepository, menuRepository, memberRepository);

		coldBrew = menuRepository.save(menu("콜드 브루", 4_900L));
		icedCoffee = menuRepository.save(menu("아이스 커피", 4_500L));

		memberRepository.save(member());
	}

	@AfterEach
	void teardown() {
		orderRepository.clear();
		menuRepository.clear();
		memberRepository.clear();
	}

	@DisplayName("새로운 주문을 등록한다.")
	@Test
	void create() {
		OrderRequestDTO request = new OrderRequestDTO(coldBrew.getId(), 1L);

		OrderResponseDTO order = orderService.create(request, member().getId());

		assertThat(order.getOrderMenus()).hasSize(1);
	}

	@DisplayName("특정 주문을 조회한다.")
	@Test
	void findById() {
		OrderResponseDTO coldBrewOrder = orderService.create(new OrderRequestDTO(coldBrew.getId(), 1L), 1L);

		OrderResponseDTO foundOrder = orderService.findById(coldBrewOrder.getId());

		assertThat(foundOrder.getOrderMenus().get(0).getMenu()).isEqualTo(new MenuResponseDTO(coldBrew));
	}

	@DisplayName("주문 목록을 조회한다.")
	@Test
	void findAll() {
		orderService.create(new OrderRequestDTO(coldBrew.getId(), 1L), 1L);
		orderService.create(new OrderRequestDTO(icedCoffee.getId(), 1L), 1L);

		List<OrderResponseDTO> orders = orderService.findAll();

		assertThat(orders).hasSize(2);
	}
}
