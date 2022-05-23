package com.jaw.cart.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<CartMenu> findAll() {
		return new ArrayList<>(cartMenus.values());
	}
}