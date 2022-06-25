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
import com.jaw.cart.ui.CartMenuOrderRequestDTO;
import com.jaw.cart.ui.CartMenuOrderResponseDTO;
import com.jaw.cart.ui.CartMenuRequestDTO;
import com.jaw.cart.ui.CartMenuResponseDTO;
import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;
import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuRepository;
import com.jaw.order.domain.Order;
import com.jaw.order.domain.OrderMenu;
import com.jaw.order.domain.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CartService {

	private final CartRepository cartRepository;
	private final CartMenuRepository cartMenuRepository;
	private final MenuRepository menuRepository;
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;

	public CartMenuResponseDTO addMenu(Long memberId, Long userId, CartMenuRequestDTO request) {
		validateUserAuthentication(memberId, userId);
		Cart cart = findCartByMemberId(memberId);
		Menu menu = menuRepository.findById(request.getMenuId())
			.orElseThrow(IllegalArgumentException::new);
		CartMenu cartMenu = cartMenuRepository.save(new CartMenu(cart, menu, request.getCount()));
		return new CartMenuResponseDTO(cartMenu);
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

	public List<CartMenuResponseDTO> findAll(Long memberId, Long userId) {
		validateUserAuthentication(memberId, userId);
		Cart cart = findCartByMemberId(memberId);
		return cartMenuRepository.findAllByCart(cart)
			.stream()
			.map(CartMenuResponseDTO::new)
			.collect(Collectors.toList());
	}

	public CartMenuOrderResponseDTO order(Long memberId, Long userId, CartMenuOrderRequestDTO request) {
		validateUserAuthentication(memberId, userId);

		Member member = memberRepository.findById(memberId)
			.orElseThrow(IllegalArgumentException::new);

		List<OrderMenu> orderMenus = request.getCartMenuIds()
			.stream()
			.map(cartMenuId -> {
				CartMenu cartMenu = cartMenuRepository.findById(cartMenuId)
					.orElseThrow(IllegalArgumentException::new);
				return new OrderMenu(cartMenu.getMenu(), cartMenu.getCount());
			})
			.collect(Collectors.toList());

		Order order = new Order(member);
		order.setOrderMenus(orderMenus);

		return new CartMenuOrderResponseDTO(orderRepository.save(order));
	}
}
