package com.jaw.cart.domain;

import java.util.List;

public interface CartMenuRepository {

	CartMenu save(CartMenu cartMenu);

	List<CartMenu> findAll();
}
