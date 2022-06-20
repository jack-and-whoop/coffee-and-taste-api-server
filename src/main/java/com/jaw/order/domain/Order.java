package com.jaw.order.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(cascade = CascadeType.REMOVE)
	private List<OrderMenu> orderMenus = new ArrayList<>();

	public Order(List<OrderMenu> orderMenus) {
		this.orderMenus = orderMenus;
	}
}
