package com.jaw.cart.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaw.cart.domain.Cart;
import com.jaw.cart.domain.CartRepository;
import com.jaw.member.application.InMemoryMemberRepository;
import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;

class CartServiceTest {

	private CartRepository cartRepository;
	private MemberRepository memberRepository;
	private CartService cartService;

	@BeforeEach
	void setup() {
		cartRepository = new InMemoryCartRepository();
		memberRepository = new InMemoryMemberRepository();
		cartService = new CartService(cartRepository, memberRepository);
	}

	@DisplayName("회원의 장바구니를 생성한다.")
	@Test
	void create() {
		Member member = memberRepository.save(member(1L, "홍길동"));
		Cart cart = cartService.create(member.getId());
		assertThat(cart.getMember()).isEqualTo(member);
	}

	private Member member(Long id, String name) {
		return Member.builder()
			.id(id)
			.name(name)
			.build();
	}

}
