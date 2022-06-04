package com.jaw.cart.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jaw.cart.application.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CartRestController {

	private final CartService cartService;

	@GetMapping("/members/{memberId}/cart")
	public ResponseEntity<List<CartMenuResponseDTO>> findAll(@PathVariable Long memberId) {
		return ResponseEntity.ok(cartService.findAll(memberId));
	}

	@PostMapping("/members/{memberId}/cart")
	public ResponseEntity<CartMenuResponseDTO> addMenu(@PathVariable Long memberId,
													   @RequestBody CartMenuRequestDTO request) {

		CartMenuResponseDTO cartMenu = cartService.addMenu(memberId, request.getMenuId(), request.getCount());
		return ResponseEntity.created(URI.create(String.format("/members/%d/cart", memberId)))
			.body(cartMenu);
	}
}
