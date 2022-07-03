package com.jaw.cart.ui;

import com.jaw.auth.UserAuthentication;
import com.jaw.cart.application.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
	public ResponseEntity<CartResponseDTO> addMenu(@RequestBody CartMenuRequestDTO request,
												   UserAuthentication authentication) {
		CartResponseDTO cart = cartService.addCartMenu(authentication.getUserId(), request);
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

	@DeleteMapping("/cart-menus")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartResponseDTO> deleteCartMenus(@RequestBody CartMenuDeleteRequest request,
														   UserAuthentication authentication) {
		CartResponseDTO cart = cartService.deleteCartMenus(authentication.getUserId(), request.getCartMenuIds());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cart);
	}

	@PostMapping("/order")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CartMenuOrderResponseDTO> order(@RequestBody CartMenuOrderRequestDTO request,
														  UserAuthentication authentication) {
		CartMenuOrderResponseDTO order = cartService.orderCartMenus(authentication.getUserId(), request);
		return ResponseEntity.ok(order);
	}
}
