package com.jaw.cart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.cart.domain.Cart;
import com.jaw.cart.domain.CartMenu;
import com.jaw.cart.domain.CartMenuRepository;
import com.jaw.cart.domain.CartRepository;
import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;
import com.jaw.menu.domain.Menu;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CartService {

	private final CartRepository cartRepository;
	private final CartMenuRepository cartMenuRepository;
	private final MemberRepository memberRepository;

	public Cart create(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(IllegalArgumentException::new);
		return cartRepository.save(new Cart(member));
	}

	public CartMenu addMenu(Long memberId, Menu menu, long count) {
		Cart cart = cartRepository.findByMemberId(memberId)
			.orElse(create(memberId));

		CartMenu cartMenu = new CartMenu(cart, menu, count);
		return cartMenuRepository.save(cartMenu);
	}
}
