package com.jaw.cart.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartMenuRequestDTO {

	private Long menuId;
	private long count;
}
