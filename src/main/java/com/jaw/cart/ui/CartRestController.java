package com.jaw.cart.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaw.cart.application.CartService;
import com.jaw.cart.domain.Cart;
import com.jaw.cart.domain.CartMenu;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CartRestController {

	private final CartService cartService;

	@PostMapping("/members/{memberId}/cart")
	public ResponseEntity<Cart> create(@PathVariable Long memberId) {
		Cart cart = cartService.create(memberId);
		return ResponseEntity.created(URI.create(String.format("/member/%d/cart", memberId)))
			.body(cart);
	}

	@GetMapping("/members/{memberId}/cart")
	public ResponseEntity<List<CartMenu>> findAll(@PathVariable Long memberId) {
		return ResponseEntity.ok(cartService.findAll(memberId));
	}
}
