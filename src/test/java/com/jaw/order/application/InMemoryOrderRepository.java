package com.jaw.order.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jaw.order.domain.Order;
import com.jaw.order.domain.OrderRepository;

public class InMemoryOrderRepository implements OrderRepository {

	private final Map<Long, Order> orders = new HashMap<>();
	private static long sequence = 0;

	@Override
	public Order save(Order order) {
		order.setId(++sequence);
		orders.put(order.getId(), order);
		return order;
	}

	@Override
	public Optional<Order> findById(Long id) {
		return Optional.ofNullable(orders.get(id));
	}

	@Override
	public List<Order> findAll() {
		return new ArrayList<>(orders.values());
	}

	public void clear() {
		sequence = 0L;
		orders.clear();
	}
}
