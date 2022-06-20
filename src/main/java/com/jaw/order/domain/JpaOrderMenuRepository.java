package com.jaw.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderMenuRepository extends OrderMenuRepository, JpaRepository<OrderMenu, Long> {
}
