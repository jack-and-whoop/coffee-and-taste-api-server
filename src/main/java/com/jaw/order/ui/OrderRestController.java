package com.jaw.order.ui;

import com.jaw.order.application.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/orders")
@RestController
public class OrderRestController {

	private final OrderService orderService;

	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO request) {
		OrderResponseDTO order = orderService.create(request);
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
