package com.jaw.cart.application;

import com.jaw.cart.domain.Cart;
import com.jaw.cart.domain.CartMenu;
import com.jaw.member.application.InMemoryMemberRepository;
import com.jaw.member.domain.Member;
import com.jaw.menu.domain.Menu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

	@DisplayName("회원의 장바구니를 생성한다.")
	@Test
	void create() {
		Member member = memberRepository.save(member( "홍길동"));
		Cart cart = cartService.create(member.getId());
		assertThat(cart.getMember()).isEqualTo(member);
	}

	@DisplayName("장바구니에 메뉴를 추가한다.")
	@Test
	void add() {
		Member member = memberRepository.save(member("고길동"));
		Cart cart = cartService.create(member.getId());
		cartService.addMenu(member.getId(), menu("바닐라 플랫 화이트", 5_900), 1);
		cartService.addMenu(member.getId(), menu("아이스 카페 모카", 5_500), 1);
		List<CartMenu> cartMenus = cartMenuRepository.findAllByCart(cart);
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
			.price(BigDecimal.valueOf(price))
			.onSale(true)
			.build();
	}

}
