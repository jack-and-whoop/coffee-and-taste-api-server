package com.jaw.cart.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/cart")
@RestController
public class CartRestController {

	private final CartService cartService;

	@GetMapping("/cart-menus")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartResponseDTO> findByUser(UserAuthentication authentication) {
		CartResponseDTO cart = cartService.findByUser(authentication.getUserId());
		return ResponseEntity.ok(cart);
	}

	@PostMapping("/cart-menus")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartMenuResponseDTO> addMenu(@RequestBody CartMenuRequestDTO request,
													   UserAuthentication authentication) {
		CartMenuResponseDTO cart = cartService.addCartMenu(authentication.getUserId(), request);
		return ResponseEntity.created(URI.create("/api/cart"))
			.body(cart);
	}

	@GetMapping("/cart-menus/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartMenuResponseDTO> findCartMenuById(@PathVariable Long id,
																UserAuthentication authentication) {
		CartMenuResponseDTO cartMenu = cartService.findCartMenuById(authentication.getUserId(), id);
		return ResponseEntity.ok(cartMenu);
	}

	@PatchMapping("/cart-menus/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartMenuResponseDTO> changeQuantity(@PathVariable Long id,
															  @RequestBody CartMenuUpdateDTO request,
															  UserAuthentication authentication) {
		CartMenuResponseDTO cartMenu = cartService.changeCartMenuQuantity(authentication.getUserId(), id, request);
		return ResponseEntity.ok(cartMenu);
	}

	@DeleteMapping("/cart-menus/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Void> deleteCartMenu(@PathVariable Long id,
														  UserAuthentication authentication) {
		cartService.deleteCartMenu(authentication.getUserId(), id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/cart-menus")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Void> deleteCartMenus(@RequestBody CartMenuDeleteRequest request,
														   UserAuthentication authentication) {
		cartService.deleteCartMenus(authentication.getUserId(), request.getCartMenuIds());
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/order")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartMenuOrderResponseDTO> order(@RequestBody CartMenuOrderRequestDTO request,
														  UserAuthentication authentication) {
		CartMenuOrderResponseDTO order = cartService.orderCartMenus(authentication.getUserId(), request);
		return ResponseEntity.ok(order);
	}
}
