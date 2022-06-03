package com.jaw.cart.ui;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
