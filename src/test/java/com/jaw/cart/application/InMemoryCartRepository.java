package com.jaw.cart.application;

import java.util.HashMap;
import java.util.Map;

import com.jaw.cart.domain.Cart;
import com.jaw.cart.domain.CartRepository;

public class InMemoryCartRepository implements CartRepository {

	private final Map<Long, Cart> carts = new HashMap<>();

	@Override
	public Cart save(Cart cart) {
		carts.put(cart.getId(), cart);
		return cart;
	}
}
