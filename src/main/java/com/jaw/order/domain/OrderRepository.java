package com.jaw.order.domain;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

	Order save(Order order);

	Optional<Order> findById(Long id);

	List<Order> findAll();

}
