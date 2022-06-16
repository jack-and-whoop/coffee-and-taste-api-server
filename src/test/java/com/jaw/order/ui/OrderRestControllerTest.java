package com.jaw.order.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.jaw.AbstractControllerTest;
import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuRepository;
import com.jaw.order.application.OrderService;

class OrderRestControllerTest extends AbstractControllerTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private MenuRepository menuRepository;

	private Menu coldBrew;
	private Menu icedCoffee;

	@BeforeEach
	protected void setup() {
		coldBrew = menuRepository.save(menu("콜드 브루", 4_900));
		icedCoffee = menuRepository.save(menu("아이스 커피", 4_500));
	}

	private Menu menu(String name, long price) {
		return Menu.builder()
			.name(name)
			.price(price)
			.build();
	}

	@DisplayName("새로운 주문을 등록한다.")
	@Test
	void create() throws Exception {
		OrderRequestDTO request = new OrderRequestDTO(coldBrew.getId(), 1L);

		mvc.perform(post("/api/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$..orderMenus[0].menu.name").value(coldBrew.getName()))
			.andExpect(jsonPath("$..orderMenus[0].menu.price").value(coldBrew.getPrice().intValue()));
	}

	@DisplayName("주문 목록을 조회한다.")
	@Test
	void findAll() throws Exception {
		OrderResponseDTO coldBrewOrder = orderService.create(new OrderRequestDTO(coldBrew.getId(), 1L));
		OrderResponseDTO icedCoffeeOrder = orderService.create(new OrderRequestDTO(icedCoffee.getId(), 1L));

		mvc.perform(get("/api/orders"))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(List.of(coldBrewOrder, icedCoffeeOrder))));
	}

	@DisplayName("특정 주문을 조회한다.")
	@Test
	void findById() throws Exception {
		OrderResponseDTO order = orderService.create(new OrderRequestDTO(coldBrew.getId(), 1L));

		mvc.perform(get("/api/orders/{id}", order.getId()))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(order)));
	}
}
