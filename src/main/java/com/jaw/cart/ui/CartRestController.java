package com.jaw.cart.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaw.auth.UserAuthentication;
import com.jaw.cart.application.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/members/{memberId}/cart")
@RestController
public class CartRestController {

	private final CartService cartService;

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<CartMenuResponseDTO>> findAll(@PathVariable Long memberId,
															 UserAuthentication authentication) {
		Long userId = authentication.getUserId();
		return ResponseEntity.ok(cartService.findAll(memberId, userId));
	}

	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartMenuResponseDTO> addMenu(@PathVariable Long memberId,
													   @RequestBody CartMenuRequestDTO request,
													   UserAuthentication authentication) {

		Long userId = authentication.getUserId();
		CartMenuResponseDTO cartMenu = cartService.addMenu(memberId, userId, request);
		return ResponseEntity.created(URI.create(String.format("/api/members/%d/cart", memberId)))
			.body(cartMenu);
	}

	@PatchMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartMenuResponseDTO> update(@PathVariable Long memberId,
													  @RequestBody CartMenuUpdateDTO request,
													  UserAuthentication authentication) {

		Long userId = authentication.getUserId();
		CartMenuResponseDTO cartMenu = cartService.update(memberId, userId, request);
		return ResponseEntity.ok(cartMenu);
	}

	@PostMapping("/order")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartMenuOrderResponseDTO> order(@PathVariable Long memberId,
														  @RequestBody CartMenuOrderRequestDTO request,
														  UserAuthentication authentication) {

		Long userId = authentication.getUserId();
		CartMenuOrderResponseDTO order = cartService.order(memberId, userId, request);
		return ResponseEntity.ok(order);
	}
}
