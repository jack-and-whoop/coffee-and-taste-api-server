package com.jaw.cart.ui;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaw.auth.UserAuthentication;
import com.jaw.cart.application.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/carts")
@RestController
public class NewCartRestController {

	private final CartService cartService;

	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartResponseDTO> create(UserAuthentication authentication) {
		CartResponseDTO cart = cartService.create(authentication.getUserId());
		return ResponseEntity.created(URI.create(String.format("/api/carts/%d", cart.getId())))
			.body(cart);
	}

	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartResponseDTO> findById(@PathVariable Long id,
													UserAuthentication authentication) {
		CartResponseDTO cart = cartService.findById(authentication.getUserId(), id);
		return ResponseEntity.ok(cart);
	}

	@PostMapping("/{id}/cart-menus")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartResponseDTO> addMenu(@PathVariable Long id,
												   @RequestBody CartMenuRequestDTO request,
												   UserAuthentication authentication) {

		CartResponseDTO cart = cartService.addCartMenu(authentication.getUserId(), id, request);
		return ResponseEntity.created(URI.create(String.format("/api/carts/%d", id)))
			.body(cart);
	}

	@DeleteMapping("/{id}/cart-menus")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartResponseDTO> deleteAllCartMenus(@PathVariable Long id,
															  UserAuthentication authentication) {

		CartResponseDTO cart = cartService.deleteAllCartMenus(authentication.getUserId(), id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cart);
	}

	@PostMapping("/{id}/order")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartMenuOrderResponseDTO> order(@PathVariable Long id,
														  @RequestBody CartMenuOrderRequestDTO request,
														  UserAuthentication authentication) {

		Long userId = authentication.getUserId();
		CartMenuOrderResponseDTO order = cartService.orderCartMenus(id, userId, request);
		return ResponseEntity.ok(order);
	}
}
