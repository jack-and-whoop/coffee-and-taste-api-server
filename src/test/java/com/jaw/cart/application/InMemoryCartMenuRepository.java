package com.jaw.cart.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jaw.cart.domain.Cart;
import com.jaw.cart.domain.CartMenu;
import com.jaw.cart.domain.CartMenuRepository;

public class InMemoryCartMenuRepository implements CartMenuRepository {

	private final Map<Long, CartMenu> cartMenus = new HashMap<>();
	private static long sequence = 0L;

	@Override
	public CartMenu save(CartMenu cartMenu) {
		cartMenu.setId(++sequence);
		cartMenus.put(cartMenu.getId(), cartMenu);
		return cartMenu;
	}

	@Override
	public List<CartMenu> findAllByCart(Cart cart) {
		return cartMenus.values().stream()
			.filter(cartMenu -> cartMenu.getCart().equals(cart))
			.collect(Collectors.toList());
	}

	@Override
	public Optional<CartMenu> findById(Long id) {
		return Optional.ofNullable(cartMenus.get(id));
	}

	@Override
	public void delete(CartMenu cartMenu) {
		cartMenus.remove(cartMenu.getId());
	}

	public void clear() {
		sequence = 0;
		cartMenus.clear();
	}
}
