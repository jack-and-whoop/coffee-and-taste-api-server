package com.jaw.cart.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaw.cart.application.CartService;
import com.jaw.cart.domain.Cart;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CartRestController {

	private final CartService cartService;

	@PostMapping("/member/{memberId}/cart")
	public ResponseEntity<Cart> create(@PathVariable Long memberId) {
		Cart cart = cartService.create(memberId);
		return ResponseEntity.created(URI.create("/member/{memberId}/cart"))
			.body(cart);
	}
}
