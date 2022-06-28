package com.jaw.cart.application;

import org.springframework.stereotype.Service;

import com.jaw.cart.domain.CartMenu;
import com.jaw.cart.domain.CartMenuRepository;
import com.jaw.cart.ui.CartMenuResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartMenuService {

	private final CartMenuRepository cartMenuRepository;

	public CartMenuResponseDTO findById(Long id) {
		CartMenu cartMenu = cartMenuRepository.findById(id)
			.orElseThrow(IllegalArgumentException::new);
		return new CartMenuResponseDTO(cartMenu);
	}

	public void changeQuantity(Long id, Long quantity) {
		CartMenu cartMenu = cartMenuRepository.findById(id)
			.orElseThrow(IllegalArgumentException::new);
		cartMenu.changeQuantity(quantity);
	}

	public void delete(Long id) {
		cartMenuRepository.deleteById(id);
	}
}
