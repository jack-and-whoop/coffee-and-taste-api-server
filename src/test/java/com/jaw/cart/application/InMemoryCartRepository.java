package com.jaw.cart.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.jaw.cart.domain.Cart;
import com.jaw.cart.domain.CartRepository;

public class InMemoryCartRepository implements CartRepository {

	private final Map<Long, Cart> carts = new HashMap<>();
	private static long sequence = 0L;

	@Override
	public Cart save(Cart cart) {
		cart.setId(++sequence);
		carts.put(cart.getId(), cart);
		return cart;
	}

	@Override
	public Optional<Cart> findByMemberId(Long memberId) {
		return carts.values().stream()
			.filter(cart -> cart.getMember().getId().equals(memberId))
			.findFirst();
	}

	@Override
	public Optional<Cart> findById(Long id) {
		return Optional.ofNullable(carts.get(id));
	}

	public void clear() {
		sequence = 0;
		carts.clear();
	}
}
