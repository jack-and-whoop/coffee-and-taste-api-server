package com.jaw.order.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.jaw.member.domain.Member;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@OneToMany(cascade = CascadeType.REMOVE)
	private List<OrderMenu> orderMenus = new ArrayList<>();

	public Order(Member member, List<OrderMenu> orderMenus) {
		this.member = member;
		this.orderMenus = orderMenus;
	}
}
