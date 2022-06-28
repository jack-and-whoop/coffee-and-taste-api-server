package com.jaw.cart.application;

import static com.jaw.Fixtures.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;

import com.jaw.cart.domain.Cart;
import com.jaw.cart.domain.CartMenu;
import com.jaw.cart.ui.CartMenuOrderRequestDTO;
import com.jaw.cart.ui.CartMenuOrderResponseDTO;
import com.jaw.cart.ui.CartMenuRequestDTO;
import com.jaw.cart.ui.CartMenuResponseDTO;
import com.jaw.cart.ui.CartResponseDTO;
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

	@DisplayName("회원은 본인의 장바구니에만 접근할 수 있다.")
	@Test
	void accessDenied() {
		Long memberId = memberRepository.save(member()).getId();
		Long otherId = memberRepository.save(other()).getId();

		Long cartId = cartService.create(memberId).getId();

		assertThatThrownBy(() -> cartService.findById(otherId, cartId))
			.isInstanceOf(AccessDeniedException.class);
	}

	@DisplayName("장바구니를 생성한다.")
	@Test
	void create() {
		Member member = memberRepository.save(member());

		CartResponseDTO cart = cartService.create(member.getId());

		assertThat(cart.getId()).isEqualTo(1L);
	}

	@DisplayName("특정 장바구니를 조회한다.")
	@Test
	void findById() {
		Member member = memberRepository.save(member());
		Cart cart = cartRepository.save(new Cart(member));
		CartMenu americano = new CartMenu(cart, menuRepository.save(menu("아메리카노", 1_000L)), 1L);
		CartMenu vanillaLatte = new CartMenu(cart, menuRepository.save(menu("바닐라 라떼", 2_000L)), 1L);
		cart.getCartMenus().addAll(List.of(americano, vanillaLatte));

		CartResponseDTO foundCart = cartService.findById(member.getId(), cart.getId());

		assertThat(foundCart.getCartMenus()).hasSize(2);
	}

	@DisplayName("장바구니 생성 요청 시, 기존에 장바구니가 있으면 해당 장바구니를 반환한다.")
	@Test
	void preExistent() {
		Member member = memberRepository.save(member());
		CartResponseDTO created = cartService.create(member.getId());

		CartResponseDTO additional = cartService.create(member.getId());

		assertThat(created.getId()).isEqualTo(additional.getId());
	}

	@DisplayName("장바구니에 담긴 모든 메뉴를 삭제한다.")
	@Test
	void deleteAllCartMenus() {
		Member member = memberRepository.save(member());
		Menu vanillaFlatWhite = menuRepository.save(menu("바닐라 플랫 화이트", 5_900L));
		Menu icedCaffeMocha = menuRepository.save(menu("아이스 카페 모카", 5_500L));
		cartService.addMenu(member.getId(), member.getId(), new CartMenuRequestDTO(vanillaFlatWhite.getId(), 1));
		cartService.addMenu(member.getId(), member.getId(), new CartMenuRequestDTO(icedCaffeMocha.getId(), 2));

		Cart cart = cartRepository.findByMemberId(member.getId())
			.orElseThrow(IllegalArgumentException::new);

		cartService.deleteAllCartMenus(member.getId(), cart.getId());

		List<CartMenu> cartMenus = cartMenuRepository.findAllByCart(cart);

		assertThat(cartMenus).isEmpty();
	}

	@DisplayName("장바구니에 메뉴를 추가한다.")
	@Test
	void addCartMenu() {
		Member member = memberRepository.save(member());
		Cart cart = cartRepository.save(new Cart(member));
		Menu vanillaFlatWhite = menuRepository.save(menu("바닐라 플랫 화이트", 5_900L));
		Menu icedCaffeMocha = menuRepository.save(menu("아이스 카페 모카", 5_500L));

		CartMenuRequestDTO vanillaFlatWhiteRequest = new CartMenuRequestDTO(vanillaFlatWhite.getId(), 1);
		CartMenuRequestDTO icedCaffeMochaRequest = new CartMenuRequestDTO(icedCaffeMocha.getId(), 2);

		cartService.addCartMenu(member.getId(), cart.getId(), vanillaFlatWhiteRequest);
		cartService.addCartMenu(member.getId(), cart.getId(), icedCaffeMochaRequest);

		List<CartMenuResponseDTO> cartMenus = cartService.findAll(member.getId(), member.getId());

		assertThat(cartMenus).hasSize(2);
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
	void orderCartMenus() {
		Member member = memberRepository.save(member());
		Cart cart = cartRepository.save(new Cart(member));
		Menu mixCoffee = menuRepository.save(menu("믹스 커피", 1_000L));
		Menu americano = menuRepository.save(menu("아메리카노", 2_000L));

		Long mixCoffeeMenuId = cartService.addMenu(member.getId(), member.getId(),
			new CartMenuRequestDTO(mixCoffee.getId(), 1)).getId();
		Long americanoMenuId = cartService.addMenu(member.getId(), member.getId(), new CartMenuRequestDTO(americano.getId(), 1))
			.getId();

		CartMenuOrderRequestDTO orderRequest = new CartMenuOrderRequestDTO();
		orderRequest.setCartMenuIds(List.of(mixCoffeeMenuId, americanoMenuId));

		CartMenuOrderResponseDTO order = cartService.orderCartMenus(cart.getId(), member.getId(), orderRequest);
		List<OrderMenuResponseDTO> orderMenus = order.getOrderMenus();

		assertThat(orderMenus).hasSize(2);
	}

	@DisplayName("장바구니에 담긴 메뉴를 주문한다.")
	@Test
	void order() {
		Member member = memberRepository.save(member());
		Menu vanillaFlatWhite = menuRepository.save(menu("바닐라 플랫 화이트", 5_900L));
		Menu icedCaffeMocha = menuRepository.save(menu("아이스 카페 모카", 5_500L));

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
