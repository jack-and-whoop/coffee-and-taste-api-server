package com.jaw.cart.application;

import static com.jaw.Fixtures.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaw.Fixtures;
import com.jaw.cart.ui.CartMenuRequestDTO;
import com.jaw.cart.ui.CartMenuResponseDTO;
import com.jaw.member.application.InMemoryMemberRepository;
import com.jaw.member.domain.Member;
import com.jaw.menu.application.InMemoryMenuRepository;
import com.jaw.menu.domain.Menu;

class CartServiceTest {

	private InMemoryCartRepository cartRepository;
	private InMemoryCartMenuRepository cartMenuRepository;
	private InMemoryMenuRepository menuRepository;
	private InMemoryMemberRepository memberRepository;
	private CartService cartService;

	@BeforeEach
	void setup() {
		cartRepository = new InMemoryCartRepository();
		cartMenuRepository = new InMemoryCartMenuRepository();
		menuRepository = new InMemoryMenuRepository();
		memberRepository = new InMemoryMemberRepository();
		cartService = new CartService(cartRepository, cartMenuRepository, menuRepository, memberRepository);
	}

	@AfterEach
	void teardown() {
		cartRepository.clear();
		cartMenuRepository.clear();
		memberRepository.clear();
	}

	@DisplayName("장바구니에 메뉴를 추가한다.")
	@Test
	void addMenu() {
		Member member = memberRepository.save(member());
		Menu vanillaFlatWhite = menuRepository.save(menu("바닐라 플랫 화이트", 5_900));
		Menu icedCaffeMocha = menuRepository.save(menu("아이스 카페 모카", 5_500));

		CartMenuRequestDTO vanillaFlatWhiteRequest = new CartMenuRequestDTO(vanillaFlatWhite.getId(), 1);
		CartMenuRequestDTO icedCaffeMochaRequest = new CartMenuRequestDTO(icedCaffeMocha.getId(), 2);

		cartService.addMenu(member.getId(), member.getId(), vanillaFlatWhiteRequest);
		cartService.addMenu(member.getId(), member.getId(), icedCaffeMochaRequest);

		List<CartMenuResponseDTO> cartMenus = cartService.findAll(member.getId(), member.getId());

		assertThat(cartMenus).hasSize(2);
	}

	private Menu menu(String name, long price) {
		return Menu.builder()
			.name(name)
			.price(price)
			.onSale(true)
			.build();
	}

}
