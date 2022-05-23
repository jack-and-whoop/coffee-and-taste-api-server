package com.jaw.cart.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.jaw.menu.domain.Menu;

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
	private Cart cart;

	@ManyToOne(fetch = FetchType.LAZY)
	private Menu menu;

	private long count;

	public CartMenu(Cart cart, Menu menu, long count) {
		this.cart = cart;
		this.menu = menu;
		this.count = count;
	}
}
