package com.jaw.order.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaw.auth.UserAuthentication;
import com.jaw.order.application.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/orders")
@RestController
public class OrderRestController {

	private final OrderService orderService;

	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO request,
												   UserAuthentication authentication) {
		OrderResponseDTO order = orderService.create(request, authentication.getUserId());
		return ResponseEntity.created(URI.create(String.format("/api/orders/%d", order.getId())))
			.body(order);
	}

	@GetMapping
	@PreAuthorize("isAuthenticated() and hasAnyRole('ADMIN')")
	public ResponseEntity<List<OrderResponseDTO>> findAll() {
		return ResponseEntity.ok(orderService.findAll());
	}

	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(orderService.findById(id));
	}
}
