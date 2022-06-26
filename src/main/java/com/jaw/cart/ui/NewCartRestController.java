package com.jaw.cart.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaw.auth.UserAuthentication;
import com.jaw.cart.application.CartService;
import com.jaw.cart.domain.Cart;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/carts")
@RestController
public class NewCartRestController {

	private final CartService cartService;

	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Cart> create(UserAuthentication authentication) {
		Cart cart = cartService.create(authentication.getUserId());
		return ResponseEntity.created(URI.create(String.format("/api/carts/%d", cart.getId())))
			.body(cart);
	}
}
