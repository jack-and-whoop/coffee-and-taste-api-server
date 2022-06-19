package com.jaw.cart.ui;

import com.jaw.auth.UserAuthentication;
import com.jaw.cart.application.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
}
