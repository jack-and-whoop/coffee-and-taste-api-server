package com.jaw.cart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaw.cart.application.CartMenuService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/cart-menus")
@RestController
public class CartMenuRestController {

	private final CartMenuService cartMenuService;

	@GetMapping("/{id}")
	public ResponseEntity<CartMenuResponseDTO> findById(@PathVariable Long id) {
		CartMenuResponseDTO cartMenu = cartMenuService.findById(id);
		return ResponseEntity.ok(cartMenu);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<CartMenuResponseDTO> changeQuantity(@PathVariable Long id,
															  @RequestBody CartMenuUpdateDTO request) {
		CartMenuResponseDTO cartMenu = cartMenuService.changeQuantity(id, request.getQuantity());
		return ResponseEntity.ok(cartMenu);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		cartMenuService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
