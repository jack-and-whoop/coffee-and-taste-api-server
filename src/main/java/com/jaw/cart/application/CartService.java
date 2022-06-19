package com.jaw.cart.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.cart.domain.Cart;
import com.jaw.cart.domain.CartMenu;
import com.jaw.cart.domain.CartMenuRepository;
import com.jaw.cart.domain.CartRepository;
import com.jaw.cart.ui.CartMenuRequestDTO;
import com.jaw.cart.ui.CartMenuResponseDTO;
import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;
import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CartService {

	private final CartRepository cartRepository;
	private final CartMenuRepository cartMenuRepository;
	private final MenuRepository menuRepository;
	private final MemberRepository memberRepository;

	public CartMenuResponseDTO addMenu(Long memberId, Long userId, CartMenuRequestDTO request) {
		validateUserAuthentication(memberId, userId);
		Cart cart = findCartByMemberId(memberId);
		Menu menu = menuRepository.findById(request.getMenuId())
			.orElseThrow(IllegalArgumentException::new);
		CartMenu cartMenu = cartMenuRepository.save(new CartMenu(cart, menu, request.getCount()));
		return new CartMenuResponseDTO(cartMenu);
	}

	@Transactional(readOnly = true)
	public List<CartMenuResponseDTO> findAll(Long memberId, Long userId) {
		validateUserAuthentication(memberId, userId);
		Cart cart = findCartByMemberId(memberId);
		return cartMenuRepository.findAllByCart(cart)
			.stream()
			.map(CartMenuResponseDTO::new)
			.collect(Collectors.toList());
	}

	private void validateUserAuthentication(Long memberId, Long userId) {
		if (!memberId.equals(userId)) {
			throw new AccessDeniedException("해당 장바구니에 접근할 수 없습니다.");
		}
	}

	private Cart findCartByMemberId(Long memberId) {
		return cartRepository.findByMemberId(memberId)
			.orElseGet(() -> createNewCart(memberId));
	}

	private Cart createNewCart(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(IllegalArgumentException::new);
		return cartRepository.save(new Cart(member));
	}
}
