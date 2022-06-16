package com.jaw.cart.ui;

import com.jaw.cart.application.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/members/{memberId}/cart")
@RestController
public class CartRestController {

	private final CartService cartService;

	@GetMapping
	public ResponseEntity<List<CartMenuResponseDTO>> findAll(@PathVariable Long memberId) {
		return ResponseEntity.ok(cartService.findAll(memberId));
	}

	@PostMapping
	public ResponseEntity<CartMenuResponseDTO> addMenu(@PathVariable Long memberId,
													   @RequestBody CartMenuRequestDTO request) {

		CartMenuResponseDTO cartMenu = cartService.addMenu(memberId, request.getMenuId(), request.getCount());
		return ResponseEntity.created(URI.create(String.format("/api/members/%d/cart", memberId)))
			.body(cartMenu);
	}
}
