package com.jaw.cart.application;

import com.jaw.cart.domain.Cart;
import com.jaw.cart.domain.CartMenu;
import com.jaw.cart.ui.*;
import com.jaw.member.application.InMemoryMemberRepository;
import com.jaw.member.domain.Member;
import com.jaw.menu.application.InMemoryMenuRepository;
import com.jaw.menu.domain.Menu;
import com.jaw.order.application.InMemoryOrderRepository;
import com.jaw.order.ui.OrderMenuResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.jaw.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
		menuRepository.clear();
		memberRepository.clear();
		orderRepository.clear();
	}

	@DisplayName("회원의 장바구니를 조회한다.")
	@Test
	void accessDenied() {
		Member member = memberRepository.save(member());
		Cart cart = cartRepository.save(new Cart(member));

		CartResponseDTO foundCart = cartService.findByUser(member.getId());

		assertThat(foundCart.getId()).isEqualTo(cart.getId());
	}

	@DisplayName("특정 장바구니를 조회한다.")
	@Test
	void findById() {
		Member member = memberRepository.save(member());
		Cart cart = cartRepository.save(new Cart(member));
		CartMenu americano = new CartMenu(cart, menuRepository.save(menu("아메리카노", 1_000L)), 1L);
		CartMenu vanillaLatte = new CartMenu(cart, menuRepository.save(menu("바닐라 라떼", 2_000L)), 1L);
		cart.getCartMenus().addAll(List.of(americano, vanillaLatte));

		CartResponseDTO foundCart = cartService.findByUser(member.getId());

		assertThat(foundCart.getCartMenus()).hasSize(2);
	}

	@DisplayName("장바구니에 담긴 메뉴를 삭제한다.")
	@Test
	void deleteCartMenus() {
		Member member = memberRepository.save(member());
		Menu vanillaFlatWhite = menuRepository.save(menu("바닐라 플랫 화이트", 5_900L));
		Menu icedCaffeMocha = menuRepository.save(menu("아이스 카페 모카", 5_500L));
		cartService.addCartMenu(member.getId(), new CartMenuRequestDTO(vanillaFlatWhite.getId(), 1));
		cartService.addCartMenu(member.getId(), new CartMenuRequestDTO(icedCaffeMocha.getId(), 2));

		Cart cart = cartRepository.findByMemberId(member.getId())
			.orElseThrow(IllegalArgumentException::new);

		cartService.deleteCartMenus(member.getId(), Arrays.asList(1L, 2L));

		List<CartMenu> cartMenus = cartMenuRepository.findAllByCart(cart);

		assertThat(cartMenus).isEmpty();
	}

	@DisplayName("삭제하고자 하는 메뉴가 장바구니에 없으면, 장바구니에 담긴 메뉴를 삭제할 수 없다.")
	@Test
	void deleteNonExistingCartMenus() {
		Member member = memberRepository.save(member());
		Menu vanillaFlatWhite = menuRepository.save(menu("바닐라 플랫 화이트", 5_900L));
		Menu icedCaffeMocha = menuRepository.save(menu("아이스 카페 모카", 5_500L));
		cartService.addCartMenu(member.getId(), new CartMenuRequestDTO(vanillaFlatWhite.getId(), 1));
		cartService.addCartMenu(member.getId(), new CartMenuRequestDTO(icedCaffeMocha.getId(), 2));

		assertThatThrownBy(() -> cartService.deleteCartMenus(member.getId(), List.of(999L)))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("장바구니에 메뉴를 추가한다.")
	@Test
	void addCartMenu() {
		Member member = memberRepository.save(member());
		Menu vanillaFlatWhite = menuRepository.save(menu("바닐라 플랫 화이트", 5_900L));
		Menu icedCaffeMocha = menuRepository.save(menu("아이스 카페 모카", 5_500L));

		CartMenuRequestDTO vanillaFlatWhiteRequest = new CartMenuRequestDTO(vanillaFlatWhite.getId(), 1);
		CartMenuRequestDTO icedCaffeMochaRequest = new CartMenuRequestDTO(icedCaffeMocha.getId(), 2);

		cartService.addCartMenu(member.getId(), vanillaFlatWhiteRequest);
		cartService.addCartMenu(member.getId(), icedCaffeMochaRequest);

		List<CartMenuResponseDTO> cartMenus = cartService.findByUser(member.getId()).getCartMenus();

		assertThat(cartMenus).hasSize(2);
	}

	@DisplayName("장바구니에 담긴 메뉴를 주문한다.")
	@Test
	void orderCartMenus() {
		Member member = memberRepository.save(member());
		Cart cart = cartRepository.save(new Cart(member));
		Menu mixCoffee = menuRepository.save(menu("믹스 커피", 1_000L));
		Menu americano = menuRepository.save(menu("아메리카노", 2_000L));

		Long mixCoffeeMenuId = cartService.addCartMenu(member.getId(), new CartMenuRequestDTO(mixCoffee.getId(), 1)).getId();
		Long americanoMenuId = cartService.addCartMenu(member.getId(), new CartMenuRequestDTO(americano.getId(), 1)).getId();

		CartMenuOrderRequestDTO orderRequest = new CartMenuOrderRequestDTO();
		orderRequest.setCartMenuIds(List.of(mixCoffeeMenuId, americanoMenuId));

		CartMenuOrderResponseDTO order = cartService.orderCartMenus(cart.getId(), orderRequest);
		List<OrderMenuResponseDTO> orderMenus = order.getOrderMenus();

		assertThat(orderMenus).hasSize(2);
	}

	@DisplayName("장바구니에 담긴 특정 메뉴를 조회한다.")
	@Test
	void findCartMenuById() {
		Member member = memberRepository.save(member());
		cartRepository.save(new Cart(member));
		Menu menu = menuRepository.save(menu("아메리카노", 1_000L));
		CartResponseDTO cartMenu = cartService.addCartMenu(member.getId(),
			new CartMenuRequestDTO(menu.getId(), 2L));

		CartMenuResponseDTO foundCartMenu = cartService.findCartMenuById(member.getId(), cartMenu.getId());

		assertThat(foundCartMenu.getMenu().getName()).isEqualTo("아메리카노");
		assertThat(foundCartMenu.getMenu().getPrice()).isEqualTo(BigDecimal.valueOf(1_000L));
		assertThat(foundCartMenu.getQuantity()).isEqualTo(2L);
		assertThat(foundCartMenu.getPrice()).isEqualTo(BigDecimal.valueOf(2_000L));
	}

	@DisplayName("장바구니 메뉴의 식별자가 유효하지 않다면, 장바구니에 담긴 메뉴를 조회할 수 없다.")
	@Test
	void findCartMenuByNonExistingId() {
		Member member = memberRepository.save(member());
		cartRepository.save(new Cart(member));
		CartMenu cartMenu = cartMenuRepository.save(new CartMenu(null, menu("아메리카노", 1_000L), 1L));

		assertThatThrownBy(() -> cartService.findCartMenuById(member.getId(), cartMenu.getId()))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("장바구니에 담긴 특정 메뉴의 수량을 변경한다.")
	@Test
	void changeCartMenuQuantity() {
		Member member = memberRepository.save(member());
		Menu menu = menuRepository.save(menu("아메리카노", 1_000L));
		CartResponseDTO cartMenu = cartService.addCartMenu(member.getId(), new CartMenuRequestDTO(menu.getId(), 1L));
		CartMenuUpdateDTO updateRequest = new CartMenuUpdateDTO(2L);

		CartMenuResponseDTO updatedCartMenu = cartService.changeCartMenuQuantity(member.getId(), cartMenu.getId(), updateRequest);

		assertThat(updatedCartMenu.getQuantity()).isEqualTo(2L);
	}

	@DisplayName("장바구니에 담긴 메뉴가 없다면, 메뉴의 수량을 변경할 수 없다.")
	@Test
	void changeCartMenuQuantityFailed() {
		Member member = memberRepository.save(member());
		Member other = memberRepository.save(other());
		Menu menu = menuRepository.save(menu("아메리카노", 1_000L));
		CartResponseDTO cartMenu = cartService.addCartMenu(other.getId(),
			new CartMenuRequestDTO(menu.getId(), 1L));
		CartMenuUpdateDTO updateRequest = new CartMenuUpdateDTO(2L);

		assertThatThrownBy(() -> cartService.changeCartMenuQuantity(member.getId(), cartMenu.getId(), updateRequest))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
