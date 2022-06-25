package com.jaw.order.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jaw.member.domain.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "order_id")
	private List<OrderMenu> orderMenus = new ArrayList<>();

	public Order(Member member) {
		this.member = member;
	}

	public void addOrderMenu(OrderMenu orderMenu) {
		orderMenus.add(orderMenu);
	}

	public void addOrderMenus(List<OrderMenu> orderMenus) {
		orderMenus.forEach(this::addOrderMenu);
	}
}
