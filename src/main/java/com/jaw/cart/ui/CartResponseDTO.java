package com.jaw.cart.ui;

import java.util.List;
import java.util.stream.Collectors;

import com.jaw.cart.domain.Cart;

import lombok.Getter;

@Getter
public class CartResponseDTO {

	private final Long id;
	private final List<CartMenuResponseDTO> cartMenus;

	public CartResponseDTO(Cart cart) {
		this.id = cart.getId();
		this.cartMenus = cart.getCartMenus()
			.stream()
			.map(CartMenuResponseDTO::new)
			.collect(Collectors.toList());
	}
}
