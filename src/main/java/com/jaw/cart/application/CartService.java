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
import com.jaw.cart.ui.CartMenuUpdateDTO;
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
		CartMenu cartMenu = cartMenuRepository.save(new CartMenu(cart, menu, request.getQuantity()));
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

		List<CartMenu> cartMenus = request.getCartMenuIds()
			.stream()
			.map(cartMenuId -> cartMenuRepository.findById(cartMenuId)
				.orElseThrow(IllegalArgumentException::new))
			.collect(Collectors.toList());

		List<OrderMenu> orderMenus = cartMenus.stream()
			.map(CartMenu::toOrderMenu)
			.collect(Collectors.toList());

		Order order = orderRepository.save(new Order(member, orderMenus));
		cartMenus.forEach(cartMenuRepository::delete);

		return new CartMenuOrderResponseDTO(order);
	}

	public CartMenuResponseDTO update(Long memberId, Long userId, CartMenuUpdateDTO request) {
		validateUserAuthentication(memberId, userId);

		CartMenu cartMenu = cartMenuRepository.findById(request.getId())
			.orElseThrow(IllegalArgumentException::new);

		cartMenu.changeQuantity(request.getQuantity());

		return new CartMenuResponseDTO(cartMenu);
	}
}
