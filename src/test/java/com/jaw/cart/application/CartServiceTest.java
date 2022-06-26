package com.jaw.cart.application;

import static com.jaw.Fixtures.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaw.cart.domain.Cart;
import com.jaw.cart.ui.CartMenuOrderRequestDTO;
import com.jaw.cart.ui.CartMenuOrderResponseDTO;
import com.jaw.cart.ui.CartMenuRequestDTO;
import com.jaw.cart.ui.CartMenuResponseDTO;
import com.jaw.member.application.InMemoryMemberRepository;
import com.jaw.member.domain.Member;
import com.jaw.menu.application.InMemoryMenuRepository;
import com.jaw.menu.domain.Menu;
import com.jaw.order.application.InMemoryOrderRepository;
import com.jaw.order.ui.OrderMenuResponseDTO;

class CartServiceTest {

	private InMemoryCartRepository cartRepository;
	private InMemoryCartMenuRepository cartMenuRepository;
	private InMemoryMenuRepository menuRepository;
	private InMemoryMemberRepository memberRepository;
	private InMemoryOrderRepository orderRepository;
	private CartService cartService;

	@BeforeEach
	void setup() {
		cartRepository = new InMemoryCartRepository();
		cartMenuRepository = new InMemoryCartMenuRepository();
		menuRepository = new InMemoryMenuRepository();
		memberRepository = new InMemoryMemberRepository();
		orderRepository = new InMemoryOrderRepository();
		cartService = new CartService(cartRepository, cartMenuRepository, menuRepository, memberRepository, orderRepository);
	}

	@AfterEach
	void teardown() {
		cartRepository.clear();
		cartMenuRepository.clear();
		memberRepository.clear();
	}

	@DisplayName("장바구니를 생성한다.")
	@Test
	void create() {
		Member member = memberRepository.save(member());

		Cart cart = cartService.create(member.getId());

		assertThat(cart.getMember()).isEqualTo(member);
	}

	@DisplayName("장바구니 생성 요청 시, 기존에 장바구니가 있으면 해당 장바구니를 반환한다.")
	@Test
	void preExistent() {
		Member member = memberRepository.save(member());
		Cart created = cartService.create(member.getId());

		Cart additional = cartService.create(member.getId());

		assertThat(created.getId()).isEqualTo(additional.getId());
	}

	@DisplayName("장바구니에 메뉴를 추가한다.")
	@Test
	void addMenu() {
		Member member = memberRepository.save(member());
		Menu vanillaFlatWhite = menuRepository.save(menu("바닐라 플랫 화이트", 5_900L));
		Menu icedCaffeMocha = menuRepository.save(menu("아이스 카페 모카", 5_500L));

		CartMenuRequestDTO vanillaFlatWhiteRequest = new CartMenuRequestDTO(vanillaFlatWhite.getId(), 1);
		CartMenuRequestDTO icedCaffeMochaRequest = new CartMenuRequestDTO(icedCaffeMocha.getId(), 2);

		cartService.addMenu(member.getId(), member.getId(), vanillaFlatWhiteRequest);
		cartService.addMenu(member.getId(), member.getId(), icedCaffeMochaRequest);

		List<CartMenuResponseDTO> cartMenus = cartService.findAll(member.getId(), member.getId());

		assertThat(cartMenus).hasSize(2);
	}

	@DisplayName("장바구니에 담긴 메뉴를 주문한다.")
	@Test
	void order() {
		Member member = memberRepository.save(member());
		Menu vanillaFlatWhite = menuRepository.save(menu("바닐라 플랫 화이트", 5_900L));
		Menu icedCaffeMocha = menuRepository.save(menu("아이스 카페 모카", 5_500L));;

		cartService.addMenu(member.getId(), member.getId(), new CartMenuRequestDTO(vanillaFlatWhite.getId(), 1));
		cartService.addMenu(member.getId(), member.getId(), new CartMenuRequestDTO(icedCaffeMocha.getId(), 1));

		List<Long> cartMenuIds = cartService.findAll(member.getId(), member.getId())
			.stream()
			.map(CartMenuResponseDTO::getId)
			.collect(Collectors.toList());

		CartMenuOrderRequestDTO orderRequest = new CartMenuOrderRequestDTO();
		orderRequest.setCartMenuIds(cartMenuIds);

		CartMenuOrderResponseDTO order = cartService.order(member.getId(), member.getId(), orderRequest);
		List<OrderMenuResponseDTO> orderMenus = order.getOrderMenus();

		assertThat(orderMenus).hasSize(2);
	}
}
