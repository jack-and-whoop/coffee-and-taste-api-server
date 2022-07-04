package com.jaw.cart.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.jaw.menu.domain.Menu;
import com.jaw.order.domain.OrderMenu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class CartMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@Setter
	private Cart cart;

	@ManyToOne(fetch = FetchType.LAZY)
	private Menu menu;

	private Long quantity;

	private BigDecimal price;

	public CartMenu(Cart cart, Menu menu, Long quantity) {
		this.cart = cart;
		this.menu = menu;
		this.quantity = quantity;
		this.price = menu.getPrice().multiply(BigDecimal.valueOf(quantity));
	}

	public OrderMenu toOrderMenu() {
		return new OrderMenu(menu, quantity);
	}

	public void changeQuantity(Long quantity) {
		this.quantity = quantity;
		this.price = price.multiply(BigDecimal.valueOf(quantity));
	}
}
