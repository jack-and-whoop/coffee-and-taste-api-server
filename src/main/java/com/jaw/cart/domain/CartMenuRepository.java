package com.jaw.cart.domain;

import java.util.List;
import java.util.Optional;

public interface CartMenuRepository {

	CartMenu save(CartMenu cartMenu);

	List<CartMenu> findAllByCart(Cart cart);

	Optional<CartMenu> findById(Long id);

	void delete(CartMenu cartMenu);

	List<CartMenu> findAllByIdIn(List<Long> ids);
}
