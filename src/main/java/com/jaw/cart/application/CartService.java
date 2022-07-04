package com.jaw.cart.application;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.jaw.cart.ui.CartResponseDTO;
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

	public CartResponseDTO findByUser(Long userId) {
		Cart cart = findByUserId(userId);
		return new CartResponseDTO(cart);
	}

	private Cart findByUserId(Long userId) {
		Member member = memberRepository.findById(userId)
			.orElseThrow(IllegalArgumentException::new);
		return cartRepository.findByMemberId(userId)
			.orElseGet(() -> cartRepository.save(new Cart(member)));
	}

	public void deleteCartMenu(Long userId, Long id) {
		Cart cart = findByUserId(userId);

		CartMenu cartMenu = cart.getCartMenus()
			.stream()
			.filter(menu -> menu.getId().equals(id))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);

		cartMenuRepository.delete(cartMenu);
	}

	public CartResponseDTO deleteCartMenus(Long userId, List<Long> ids) {
		Cart cart = findByUserId(userId);
		List<CartMenu> cartMenus = cart.getCartMenus();

		Set<Long> cartMenuIds = cartMenus.stream()
			.map(CartMenu::getId)
			.collect(Collectors.toSet());

		if (!cartMenuIds.containsAll(ids)) {
			throw new IllegalArgumentException();
		}

		cartMenus.forEach(cartMenuRepository::delete);

		return new CartResponseDTO(cart);
	}

	public CartMenuResponseDTO addCartMenu(Long userId, CartMenuRequestDTO request) {
		Cart cart = findByUserId(userId);
		Menu menu = menuRepository.findById(request.getMenuId())
			.orElseThrow(IllegalArgumentException::new);
		CartMenu cartMenu = cartMenuRepository.save(new CartMenu(cart, menu, request.getQuantity()));
		cart.addMenu(cartMenu);
		return new CartMenuResponseDTO(cartMenu);
	}

	public CartMenuOrderResponseDTO orderCartMenus(Long userId, CartMenuOrderRequestDTO request) {
		Member member = memberRepository.findById(userId)
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

	public CartMenuResponseDTO findCartMenuById(Long userId, Long id) {
		CartMenu cartMenu = findCartMenu(userId, id);
		return new CartMenuResponseDTO(cartMenu);
	}

	private CartMenu findCartMenu(Long userId, Long id) {
		Cart cart = cartRepository.findByMemberId(userId)
			.orElseThrow(IllegalArgumentException::new);

		CartMenu cartMenu = cartMenuRepository.findById(id)
			.orElseThrow(IllegalArgumentException::new);

		if (!cart.getCartMenus().contains(cartMenu)) {
			throw new IllegalArgumentException();
		}

		return cartMenu;
	}

	public CartMenuResponseDTO changeCartMenuQuantity(Long userId, Long cartMenuId, CartMenuUpdateDTO request) {
		CartMenu cartMenu = findCartMenu(userId, cartMenuId);
		cartMenu.changeQuantity(request.getQuantity());
		return new CartMenuResponseDTO(cartMenu);
	}
}
