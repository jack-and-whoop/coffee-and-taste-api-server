package com.jaw.cart.application;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaw.cart.ui.CartMenuResponseDTO;
import com.jaw.member.application.InMemoryMemberRepository;
import com.jaw.member.domain.Member;
import com.jaw.menu.domain.Menu;

class CartServiceTest {

	private InMemoryCartRepository cartRepository;
	private InMemoryCartMenuRepository cartMenuRepository;
	private InMemoryMemberRepository memberRepository;
	private CartService cartService;

	@BeforeEach
	void setup() {
		cartRepository = new InMemoryCartRepository();
		cartMenuRepository = new InMemoryCartMenuRepository();
		memberRepository = new InMemoryMemberRepository();
		cartService = new CartService(cartRepository, cartMenuRepository, memberRepository);
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
		Member member = memberRepository.save(member("고길동"));

		cartService.addMenu(member.getId(), menu("바닐라 플랫 화이트", 5_900), 1);
		cartService.addMenu(member.getId(), menu("아이스 카페 모카", 5_500), 1);

		List<CartMenuResponseDTO> cartMenus = cartService.findAll(member.getId());

		assertThat(cartMenus).hasSize(2);
	}

	private Member member(String name) {
		return Member.builder()
			.name(name)
			.build();
	}

	private Menu menu(String name, long price) {
		return Menu.builder()
			.name(name)
			.price(price)
			.onSale(true)
			.build();
	}

}
