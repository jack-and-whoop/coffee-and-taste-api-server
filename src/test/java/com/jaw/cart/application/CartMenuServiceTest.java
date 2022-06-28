package com.jaw.cart.application;

import static com.jaw.Fixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaw.cart.domain.Cart;
import com.jaw.cart.domain.CartMenu;
import com.jaw.cart.ui.CartMenuResponseDTO;

class CartMenuServiceTest {

	private InMemoryCartMenuRepository cartMenuRepository;
	private CartMenuService cartMenuService;

	@BeforeEach
	void setup() {
		cartMenuRepository = new InMemoryCartMenuRepository();
		cartMenuService = new CartMenuService(cartMenuRepository);
	}

	@DisplayName("특정 장바구니 메뉴를 조회한다.")
	@Test
	void findById() {
		Cart cart = new Cart(member());
		CartMenu icedAmericano = cartMenuRepository.save(new CartMenu(cart, menu("아이스 아메리카노", 1_000L), 1L));

		CartMenuResponseDTO cartMenu = cartMenuService.findById(icedAmericano.getId());

		assertAll(
			() -> assertThat(cartMenu.getMenu().getName()).isEqualTo("아이스 아메리카노"),
			() -> assertThat(cartMenu.getMenu().getPrice()).isEqualTo(BigDecimal.valueOf(1_000L)),
			() -> assertThat(cartMenu.getQuantity()).isEqualTo(1L)
		);
	}

	@DisplayName("장바구니에 담긴 메뉴의 수량을 변경한다.")
	@Test
	void changeQuantity() {
		Cart cart = new Cart(member());
		CartMenu icedAmericano = cartMenuRepository.save(new CartMenu(cart, menu("아이스 아메리카노", 1_000L), 1L));
		cartMenuService.changeQuantity(icedAmericano.getId(), 2L);

		CartMenuResponseDTO cartMenu = cartMenuService.findById(icedAmericano.getId());

		assertThat(cartMenu.getQuantity()).isEqualTo(2L);
	}

	@DisplayName("장바구니에 담긴 메뉴를 삭제한다.")
	@Test
	void delete() {
		Cart cart = new Cart(member());
		CartMenu icedAmericano = cartMenuRepository.save(new CartMenu(cart, menu("아이스 아메리카노", 1_000L), 1L));
		Long cartMenuId = icedAmericano.getId();

		cartMenuService.delete(cartMenuId);

		assertThatThrownBy(() -> cartMenuService.findById(cartMenuId))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
